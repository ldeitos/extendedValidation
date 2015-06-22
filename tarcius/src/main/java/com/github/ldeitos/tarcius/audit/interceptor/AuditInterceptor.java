package com.github.ldeitos.tarcius.audit.interceptor;

import static com.github.ldeitos.tarcius.configuration.Constants.FORMATTED_DATE_RESOLVER;
import static com.github.ldeitos.tarcius.configuration.Constants.FORMATTED_STRING_RESOLVER;
import static com.github.ldeitos.tarcius.configuration.Constants.STRING_RESOLVER;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
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
import com.github.ldeitos.tarcius.api.ParameterFormattedResolver;
import com.github.ldeitos.tarcius.api.ParameterResolver;
import com.github.ldeitos.tarcius.api.annotation.Audit;
import com.github.ldeitos.tarcius.api.annotation.Audited;
import com.github.ldeitos.tarcius.audit.AuditContext;
import com.github.ldeitos.tarcius.audit.AuditDataSource;
import com.github.ldeitos.tarcius.audit.factory.AuditDataDispatcherFactory;
import com.github.ldeitos.tarcius.audit.factory.AuditDataFormatterFactory;
import com.github.ldeitos.tarcius.audit.factory.ResolverFactory;
import com.github.ldeitos.tarcius.configuration.ConfigInfoProvider;
import com.github.ldeitos.tarcius.configuration.Configuration;
import com.github.ldeitos.tarcius.exception.AuditException;
import com.github.ldeitos.tarcius.exception.InvalidConfigurationException;
import com.github.ldeitos.tarcius.qualifier.CustomResolver;

@Audit
@Any
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

	@Inject
	private ConfigInfoProvider configInfoProvider;

	@AroundInvoke
	public Object doAudit(InvocationContext invCtx) throws Exception {
		try {
			AuditDataSource auditDataSource = resolveAuditDataSource(invCtx);
			AuditDataContainer<?> auditData = formatAuditData(auditDataSource);
			dispatchAuditData(auditData);
		} catch (Exception e) {
			String msg = format("Unable to do audit process. Cause: [%s] ", e.getMessage());

			if (getConfiguration().mustInterruptOnError()) {
				logger.error(msg, e);

				throw new AuditException(msg, e);
			} else {
				logger.warn(msg, e);
			}
		}

		return invCtx.proceed();
	}

	private Configuration getConfiguration() throws InvalidConfigurationException {
		return Configuration.getConfiguration(configInfoProvider);
	}

	private AuditDataSource resolveAuditDataSource(InvocationContext invCtx) {
		String auditRef = parseAuditReference(invCtx);
		List<AuditedParameter> toAudit = getParametersToAudit(invCtx);
		AuditDataSource auditDataSource = new AuditDataSource(auditRef);

		context.get().addAuditEntry(auditRef, auditDataSource);

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

	private void resolveAndFillParametersValues(AuditDataSource auditDataSource,
	    List<AuditedParameter> toAudit) {
		for (AuditedParameter auditedParameter : toAudit) {
			Audited auditedParameterConfig = auditedParameter.getConfig();
			String auditRef = auditedParameterConfig.auditRef();

			if (auditDataSource.getResolvedParameterValues().containsKey(auditRef)) {
				logger.warn(format("More than one parameter have a same reference [%s]. "
				    + "Only the first will be considered.", auditRef));
				continue;
			}

			String resolvedValue = resolveParameterValue(auditedParameter);
			auditDataSource.addParameterValue(auditRef, auditedParameter.getParameter(), resolvedValue);
		}
	}

	private String resolveParameterValue(AuditedParameter auditedParameter) {
		Audited auditedParameterConfig = auditedParameter.getConfig();
		CustomResolver resolverQualifier = getResolverQualifier(auditedParameter);

		if (resolverQualifier.equals(STRING_RESOLVER) && isNotBlank(auditedParameterConfig.format())) {
			return getFormattedResolver(auditedParameter).resolve(auditedParameterConfig.format(),
			    auditedParameter.getParameter());
		}

		ParameterResolver<Object> resolver = getResolver(resolverQualifier);

		return resolver.resolve(auditedParameter.getParameter());
	}

	@SuppressWarnings("unchecked")
	private ParameterResolver<Object> getResolver(CustomResolver resolverQualifier) {

		ParameterResolver<Object> resolver = (ParameterResolver<Object>) resolverFactory
		    .getResolver(resolverQualifier);

		return resolver;

	}

	@SuppressWarnings("unchecked")
	private ParameterFormattedResolver<Object> getFormattedResolver(AuditedParameter auditedParameter) {
		CustomResolver resolverQualifier = FORMATTED_STRING_RESOLVER;

		if (auditedParameter.getParameter() instanceof Date) {
			resolverQualifier = FORMATTED_DATE_RESOLVER;
		}

		ParameterFormattedResolver<Object> resolver = (ParameterFormattedResolver<Object>) resolverFactory
		    .getFormattedResolver(resolverQualifier);

		return resolver;
	}

	private CustomResolver getResolverQualifier(AuditedParameter auditedParameter) {
		CustomResolver qualifier;
		Audited config = auditedParameter.getConfig();

		switch (config.translator()) {
		case CUSTOM:
			qualifier = config.customResolverQualifier();
			break;
		default:
			qualifier = config.translator().getResolverQualifier();
		}

		return qualifier;
	}

	private AuditDataContainer<?> formatAuditData(AuditDataSource auditDataSource)
		throws InvalidConfigurationException {
		AuditDataFormatter<?> auditDataFormatter = auditDataFormatterFactory.getCurrentFormatter();
		AuditDataContainer<?> formattedAuditData = auditDataFormatter.format(auditDataSource);
		return formattedAuditData;
	}

	@SuppressWarnings("unchecked")
	private void dispatchAuditData(AuditDataContainer<?> auditDataContainer)
		throws InvalidConfigurationException {
		AuditDataDispatcher<?> dispatcher = auditDataDispatcherFactory.getCurrentDispatcher();

		((AuditDataDispatcher<Object>) dispatcher).dispatch(auditDataContainer.getAuditData());
	}

	private class AuditedParameter {
		private Audited config;

		private Object auditedParameter;

		AuditedParameter(Audited conf, Object parameter) {
			config = conf;
			auditedParameter = parameter;
		}

		public Audited getConfig() {
			return config;
		}

		public Object getParameter() {
			return auditedParameter;
		}
	}
}
