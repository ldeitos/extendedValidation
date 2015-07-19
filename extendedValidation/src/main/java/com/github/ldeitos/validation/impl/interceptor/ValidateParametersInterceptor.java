package com.github.ldeitos.validation.impl.interceptor;

import static com.github.ldeitos.constants.Constants.DEFAULT_VALIDATION_CLOSURE_QUALIFIER;
import static com.github.ldeitos.validation.impl.configuration.Configuration.getConfiguration;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

import com.github.ldeitos.qualifier.Closure;
import com.github.ldeitos.qualifier.ExtendedValidator;
import com.github.ldeitos.util.ManualContext;
import com.github.ldeitos.validation.Message;
import com.github.ldeitos.validation.ValidationClosure;
import com.github.ldeitos.validation.Validator;
import com.github.ldeitos.validation.annotation.SkipValidation;
import com.github.ldeitos.validation.annotation.ValidateParameters;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;

/**
 * CDI based interceptor to do audit process.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @since 0.9.2
 */
@ValidateParameters
@Any
@Interceptor
public class ValidateParametersInterceptor {
	private Logger logger = getLogger(getClass());

	@Inject
	@ExtendedValidator
	private Validator validator;

	@Inject
	private ConfigInfoProvider configProvider;

	/**
	 * Validation interceptor method.
	 *
	 * @param invCtx
	 *            {@link InvocationContext}
	 * @return Intercepted method result.
	 *
	 * @throws Exception
	 *
	 */
	@AroundInvoke
	public Object doAudit(InvocationContext invCtx) throws Exception {
		Set<Message> violations = doValidation(invCtx);

		if (isNotEmpty(violations)) {
			ValidationClosure closure = getClosure(invCtx);

			closure.proceed(violations);
			logger.info("Validation process successfuly completed.");
		}

		return invCtx.proceed();
	}

	private Set<Message> doValidation(InvocationContext invCtx) {
		Method invokedMethod = invCtx.getMethod();
		logger.info(format("Init method [%s] parameters validation process.", invokedMethod.getName()));
		Set<Message> violations = new HashSet<Message>();
		Set<Message> objectViolations = new HashSet<Message>();
		Annotation[][] parameterAnnotations = invokedMethod.getParameterAnnotations();
		Object[] toValidate = getParametersToValidate(invCtx.getParameters(), parameterAnnotations);

		for (Object object : toValidate) {
			if (logger.isDebugEnabled()) {
				logger.debug(format("Doing parameter [%s] validation. Parameter class: [%s]",
				    valueOf(object), object.getClass().getSimpleName()));
			}

			objectViolations.addAll(validator.validateBean(object, getValidationGroups(invCtx)));

			violations.addAll(objectViolations);

			if (isNotEmpty(objectViolations)) {
				logger.debug("Violations found during validation process.");
				objectViolations.clear();
			}
		}

		return violations;
	}

	private Class<?>[] getValidationGroups(InvocationContext invCtx) {
		ValidateParameters conf = getInterceptorConfiguration(invCtx);
		return conf.groups();
	}

	private ValidateParameters getInterceptorConfiguration(InvocationContext invCtx) {
		ValidateParameters conf = invCtx.getMethod().getAnnotation(ValidateParameters.class);

		if (conf == null) {
			Class<? extends Object> invokedTarget = invCtx.getTarget().getClass();
			conf = invokedTarget.getAnnotation(ValidateParameters.class);
		}

		return conf;
	}

	private Object[] getParametersToValidate(Object[] parameters, Annotation[][] parameterAnnotations) {
		List<Object> result = new ArrayList<Object>();

		for (int i = 0; i < parameterAnnotations.length; i++) {
			Object parameter = parameters[i];

			if (assertNotNull(parameter)) {
				if (logger.isDebugEnabled()) {
					String log = format("Processing parameter [%s], parameter index [%d].",
						valueOf(parameter), i);
					logger.debug(log);
				}

				Annotation[] annotations = parameterAnnotations[i];
				SkipValidation skipped = getAnnotation(annotations, SkipValidation.class);

				if (skipped == null) {
					logger.debug(format("Parameter [%s] added to validation process.", valueOf(parameter)));
					result.add(parameter);
				}
			}
		}

		logParamtersToValidate(result);

		return result.toArray();
	}

	private boolean assertNotNull(Object parameter) {
		boolean notNull = parameter != null;

		if (!notNull && logger.isDebugEnabled()) {
			logger.debug("Parameter value is null, skipping.");
		}

		return notNull;
	}

	private void logParamtersToValidate(List<Object> toValidate) {
		if (isEmpty(toValidate)) {
			logger.warn("Has no parameters to be validated.");
		} else {
			logger.debug(format("Found %d paramters to validate.", toValidate.size()));
		}
	}

	private ValidationClosure getClosure(InvocationContext invCtx) {

		ValidationClosure closure = getConfiguration(configProvider).getConfiguredValidationClosure();
		ValidateParameters conf = getInterceptorConfiguration(invCtx);

		if (mustUseSpecificClosure(conf.closure())) {
			closure = ManualContext.lookupCDI(ValidationClosure.class, conf.closure());
		}

		return closure;
	}

	private boolean mustUseSpecificClosure(Closure qualifier) {
		String stringQualifier = qualifier.value();

		return !stringQualifier.equals(DEFAULT_VALIDATION_CLOSURE_QUALIFIER);
	}

	@SuppressWarnings("unchecked")
	private <T> T getAnnotation(Annotation[] annotations, Class<T> annotationClass) {
		for (Annotation annotation : annotations) {
			if (annotationClass.isAssignableFrom(annotation.getClass())) {
				logger.debug("Parameter identified with @SkipValidation annotation, will be ignored.");
				return (T) annotation;
			}
		}

		return null;
	}
}
