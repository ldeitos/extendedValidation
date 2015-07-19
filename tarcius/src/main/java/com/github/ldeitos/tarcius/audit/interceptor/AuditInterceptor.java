package com.github.ldeitos.tarcius.audit.interceptor;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

import com.github.ldeitos.tarcius.api.annotation.Audit;
import com.github.ldeitos.tarcius.audit.AuditProcessor;

/**
 * CDI based interceptor to do audit process.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@Audit
@Any
@Interceptor
public class AuditInterceptor {
	private Logger logger = getLogger(getClass());

	@Inject
	private AuditProcessor auditProcessor;

	/**
	 * Audit interceptor method.
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
		logger.info("Init audit process.");

		String auditRef = parseAuditReference(invCtx);
		Object[] parameters = invCtx.getParameters();
		Method invokedMethod = invCtx.getMethod();
		Annotation[][] parameterAnnotations = invokedMethod.getParameterAnnotations();

		auditProcessor.doAudit(auditRef, parameters, parameterAnnotations);

		logger.info("Audit process successfuly completed.");

		return invCtx.proceed();
	}

	private String parseAuditReference(InvocationContext invCtx) {
		String result = invCtx.getMethod().getName();
		Audit conf = getAuditConfiguration(invCtx);

		logger.debug(format("Auditing method invocation. Method: [%s]", result));

		if (isNotBlank(conf.auditRef())) {
			result = conf.auditRef();
		}

		logger.debug(format("Audit reference: [%s]", result));

		return result;
	}

	private Audit getAuditConfiguration(InvocationContext invCtx) {
		Audit conf = invCtx.getMethod().getAnnotation(Audit.class);

		if (conf == null) {
			Class<? extends Object> targetClass = invCtx.getTarget().getClass();
			conf = targetClass.getAnnotation(Audit.class);
		}

		return conf;
	}
}
