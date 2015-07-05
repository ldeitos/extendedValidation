package com.github.ldeitos.tarcius.audit.auditprocessor;

import static com.github.ldeitos.tarcius.configuration.Constants.FORMATTED_DATE_RESOLVER;
import static com.github.ldeitos.tarcius.configuration.Constants.FORMATTED_STRING_RESOLVER;
import static com.github.ldeitos.tarcius.configuration.Constants.STRING_RESOLVER;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.apache.commons.lang3.ArrayUtils.addAll;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.github.ldeitos.tarcius.api.AuditDataContainer;
import com.github.ldeitos.tarcius.api.AuditDataDispatcher;
import com.github.ldeitos.tarcius.api.AuditDataFormatter;
import com.github.ldeitos.tarcius.api.ParameterFormattedResolver;
import com.github.ldeitos.tarcius.api.ParameterResolver;
import com.github.ldeitos.tarcius.api.annotation.Audited;
import com.github.ldeitos.tarcius.api.annotation.NotAudited;
import com.github.ldeitos.tarcius.audit.AuditContext;
import com.github.ldeitos.tarcius.audit.AuditDataSource;
import com.github.ldeitos.tarcius.audit.AuditProcessor;
import com.github.ldeitos.tarcius.audit.factory.AuditDataDispatcherFactory;
import com.github.ldeitos.tarcius.audit.factory.AuditDataFormatterFactory;
import com.github.ldeitos.tarcius.audit.factory.ResolverFactory;
import com.github.ldeitos.tarcius.configuration.ConfigInfoProvider;
import com.github.ldeitos.tarcius.configuration.Configuration;
import com.github.ldeitos.tarcius.exception.AuditException;
import com.github.ldeitos.tarcius.exception.InvalidConfigurationException;
import com.github.ldeitos.tarcius.qualifier.CustomResolver;

/**
 * Audit processor implementation.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @see AuditProcessor
 *
 * @since 0.1.2
 */
public class AuditProcessorImpl implements AuditProcessor {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doAudit(String auditRef, Object... parameters) throws AuditException {
		Annotation[][] parameterAnnotations = new Annotation[parameters.length][0];
		doAudit(auditRef, parameters, parameterAnnotations);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doAudit(String auditRef, Object[] parameters, Annotation[][] parameterAnnotations)
	    throws AuditException {
		try {
			AuditDataSource auditDataSource = resolveAuditDataSource(auditRef, parameters,
			    parameterAnnotations);
			AuditDataContainer<?> auditData = formatAuditData(auditDataSource);
			dispatchAuditData(auditData);
		} catch (Exception e) {
			String msg = format("Unable to do audit process. Cause: [%s] ", e.getMessage());

			if (getConfiguration().mustInterruptOnError()) {
				logger.error(msg, e);

				throw new AuditException(msg, e);
			} else {
				logger.warn(msg, e);
				logger.warn("Tarcius engine configured to ignore errors.");
			}
		}
	}

	private Configuration getConfiguration() {
		try {
			return Configuration.getConfiguration(configInfoProvider);
		} catch (InvalidConfigurationException e) {
			throw new AuditException(e.getMessage(), e);
		}
	}

	private AuditDataSource resolveAuditDataSource(String auditRef, Object[] parameters,
		Annotation[][] parameterAnnotations) {
		AuditDataSource auditDataSource = new AuditDataSource(auditRef);
		List<AuditedParameter> toAudit = getParametersToAudit(parameters, parameterAnnotations);

		context.get().addAuditEntry(auditRef, auditDataSource);

		resolveAndFillParametersValues(auditDataSource, toAudit);

		return auditDataSource;
	}

	private List<AuditedParameter> getParametersToAudit(Object[] parameters,
		Annotation[][] parameterAnnotations) {
		List<AuditedParameter> result = new ArrayList<AuditedParameter>();

		for (int i = 0; i < parameterAnnotations.length; i++) {
			Object parameter = parameters[i];

			if (logger.isTraceEnabled()) {
				String log = format("Processing parameter [%s], parameter index [%d].", valueOf(parameter), i);
				logger.trace(log);
			}

			Class<? extends Object> parameterClass = parameter.getClass();
			Annotation[] annotations = addAll(parameterAnnotations[i], parameterClass.getAnnotations());
			Audited audited = getAuditedAnnotation(annotations, Audited.class);
			NotAudited notAudited = getAuditedAnnotation(annotations, NotAudited.class);

			if (audited != null && notAudited == null) {
				logger.trace("Parameter added to audit process.");
				result.add(new AuditedParameter(audited, parameter));
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private <T> T getAuditedAnnotation(Annotation[] annotations, Class<T> annotationClass) {
		for (Annotation annotation : annotations) {
			if (annotationClass.isAssignableFrom(annotation.getClass())) {
				if (annotationClass.equals(NotAudited.class)) {
					logger.trace("Parameter identified with @NotAudited annotation, will be ignored.");
				}
				return (T) annotation;
			}
		}

		if (annotationClass.equals(Audited.class)) {
			logger.trace("Parameter not identified with @Audited annotation, will be ignored.");
		}

		return null;
	}

	private void resolveAndFillParametersValues(AuditDataSource auditDataSource,
	    List<AuditedParameter> toAudit) {
		Map<String, Integer> keyOccurrence = new HashMap<String, Integer>();

		for (AuditedParameter auditedParameter : toAudit) {
			Audited auditedParameterConfig = auditedParameter.getConfig();
			String auditRef = auditedParameterConfig.auditRef();

			logTrace("Audit reference to parameter [%s]: [%s]", auditedParameterConfig, auditRef);

			auditRef = adjustIfDupplicated(auditRef, keyOccurrence);

			String resolvedValue = resolveParameterValue(auditedParameter);

			logTrace("Parameter [%s] resolved to [%s]", auditedParameter, resolvedValue);

			auditDataSource.addParameterValue(auditRef, auditedParameter.getParameter(), resolvedValue);
		}
	}

	private String adjustIfDupplicated(String auditRef, Map<String, Integer> keyOccurrence) {
		if (keyOccurrence.containsKey(auditRef)) {
			String oldReference = auditRef;
			int occurrence = keyOccurrence.get(auditRef);

			keyOccurrence.put(auditRef, ++occurrence);

			auditRef += occurrence;

			logger.warn(format("More than one parameter have a same reference [%s]. "
			    + "The reference will be changed to [%s].", oldReference, auditRef));
		} else {
			keyOccurrence.put(auditRef, 0);
		}

		return auditRef;
	}

	private void logTrace(String logMsg, Object... params) {
		if (logger.isTraceEnabled()) {
			String log = format(logMsg, params);
			logger.trace(log);
		}
	}

	private String resolveParameterValue(AuditedParameter auditedParameter) {
		Audited auditedParameterConfig = auditedParameter.getConfig();
		Annotation resolverQualifier = getResolverQualifier(auditedParameter);

		if (resolverQualifier.equals(STRING_RESOLVER) && isNotBlank(auditedParameterConfig.format())) {
			return getFormattedResolver(auditedParameter).resolve(auditedParameterConfig.format(),
			    auditedParameter.getParameter());
		}

		ParameterResolver<Object> resolver = getResolver(resolverQualifier);

		return resolver.resolve(auditedParameter.getParameter());
	}

	@SuppressWarnings("unchecked")
	private ParameterResolver<Object> getResolver(Annotation resolverQualifier) {

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

	private Annotation getResolverQualifier(AuditedParameter auditedParameter) {
		Annotation qualifier;
		Audited config = auditedParameter.getConfig();

		switch (config.translator()) {
		case CUSTOM:
			qualifier = config.customResolverQualifier();
			break;
		default:
			qualifier = config.translator().getResolverQualifier();
		}

		logger.trace(format("Resolver qualifier: [%s]", qualifier));

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
