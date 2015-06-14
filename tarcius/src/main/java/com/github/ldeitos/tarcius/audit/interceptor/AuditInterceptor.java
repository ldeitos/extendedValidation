package com.github.ldeitos.tarcius.audit.interceptor;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

import com.github.ldeitos.tarcius.api.AuditDataContainer;
import com.github.ldeitos.tarcius.api.AuditDataDispatcher;
import com.github.ldeitos.tarcius.api.AuditDataFormatter;
import com.github.ldeitos.tarcius.api.ParameterResolver;
import com.github.ldeitos.tarcius.api.annotation.Audit;
import com.github.ldeitos.tarcius.api.annotation.Audited;
import com.github.ldeitos.tarcius.audit.AuditContext;
import com.github.ldeitos.tarcius.audit.AuditDataSource;
import com.github.ldeitos.tarcius.audit.factory.AuditDataDispatcherFactory;
import com.github.ldeitos.tarcius.audit.factory.AuditDataFormatterFactory;
import com.github.ldeitos.tarcius.audit.factory.ResolverFactory;
import com.github.ldeitos.tarcius.qualifier.CustomResolver;

@Audit
@Interceptor
public class AuditInterceptor {
	private Logger logger = getLogger(getClass());

	@Inject
	@Any
	private Instance<AuditContext> context;

	@Inject
	private AuditDataFormatterFactory auditDataFormatterFactory;

	@Inject
	private AuditDataDispatcherFactory auditDataDispatcherFactory;

	@Inject
	private ResolverFactory resolverFactory;

	@AroundInvoke
	public Object doAudit(InvocationContext invCtx) throws Exception {
		try {
			AuditDataSource auditDataSource = resolveAuditDataSource(invCtx);
			AuditDataContainer<?> auditData = formatAuditData(auditDataSource);
			dispatchAuditData(auditData);
		} catch (Exception e) {
			logger.warn("", e);
		}

		return invCtx.proceed();
	}

	private AuditDataSource resolveAuditDataSource(InvocationContext invCtx) {
		String auditRef = parseAuditReference(invCtx);
		List<AuditedParameter> toAudit = getParametersToAudit(invCtx);
		AuditDataSource auditDataSource = new AuditDataSource(auditRef);

		resolveAndFillParametersValues(auditDataSource, toAudit);

		return auditDataSource;
	}

	private String parseAuditReference(InvocationContext invCtx) {
		String result = invCtx.getMethod().getName();
		Audit conf = invCtx.getMethod().getAnnotation(Audit.class);

		if (isNotBlank(conf.auditRef())) {
			result = conf.auditRef();
		}

		return result;
	}

	private List<AuditedParameter> getParametersToAudit(InvocationContext invCtx) {
		Method invokedMethod = invCtx.getMethod();
		Object[] parameters = invCtx.getParameters();
		List<AuditedParameter> result = new ArrayList<AuditedParameter>();
		Annotation[][] parameterAnnotations = invokedMethod.getParameterAnnotations();

		for (int i = 0; i < parameterAnnotations.length; i++) {
			Audited audited = getAuditedAnnotation(parameterAnnotations[i]);

			if (audited != null) {
				result.add(new AuditedParameter(audited, parameters[i]));
			}
		}

		return result;
	}

	private Audited getAuditedAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation instanceof Audited) {
				return (Audited) annotation;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private void resolveAndFillParametersValues(AuditDataSource auditDataSource,
		List<AuditedParameter> toAudit) {
		for (AuditedParameter auditedParameter : toAudit) {
			Audited auditedParameterConfig = auditedParameter.getConf();
			String auditRef = auditedParameterConfig.auditRef();
			CustomResolver resolverQualifier = getResolverQualifier(auditedParameterConfig);
			ParameterResolver<Object> resolver = (ParameterResolver<Object>) resolverFactory
			    .getResolver(resolverQualifier);

			auditDataSource.addParameterValue(auditRef, resolver.resolve(auditedParameter.getParameter()));
		}
	}

	private CustomResolver getResolverQualifier(Audited auditedParameterConfig) {
		CustomResolver qualifier;

		switch (auditedParameterConfig.translator()) {
		case CUSTOM:
			qualifier = auditedParameterConfig.customResolverQualifier();
		default:
			qualifier = auditedParameterConfig.translator().getResolverQualifier();
		}

		return qualifier;
	}

	private AuditDataContainer<?> formatAuditData(AuditDataSource auditDataSource) {
		AuditDataFormatter<?> auditDataFormatter = auditDataFormatterFactory.getCurrentFormatter();
		AuditDataContainer<?> formattedAuditData = auditDataFormatter.format(auditDataSource);
		return formattedAuditData;
	}

	@SuppressWarnings("unchecked")
	private void dispatchAuditData(AuditDataContainer<?> auditDataContainer) {
		AuditDataDispatcher<?> dispatcher = auditDataDispatcherFactory.getCurrentDispatcher();

		((AuditDataDispatcher<Object>) dispatcher).dispatch(auditDataContainer.getAuditData());
	}

	private class AuditedParameter {
		private Audited conf;

		private Object parameter;

		AuditedParameter(Audited conf, Object parameter) {
			this.conf = conf;
			this.parameter = parameter;
		}

		public Audited getConf() {
			return conf;
		}

		public Object getParameter() {
			return parameter;
		}
	}
}
