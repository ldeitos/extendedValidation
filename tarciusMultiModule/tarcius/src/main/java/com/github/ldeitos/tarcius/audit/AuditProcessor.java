package com.github.ldeitos.tarcius.audit;

import java.lang.annotation.Annotation;

import com.github.ldeitos.tarcius.api.annotation.Audited;
import com.github.ldeitos.tarcius.configuration.AuditPhases;
import com.github.ldeitos.tarcius.configuration.Constants;
import com.github.ldeitos.tarcius.exception.AuditException;

/**
 * Interface to audit processor.<br>
 *
 * This will process three audit phases:<br>
 * - {@link AuditPhases#AUDITED_PARAMETER_RESOLVE}<br>
 * - {@link AuditPhases#AUDIT_FORMAT}<br>
 * - {@link AuditPhases#AUDIT_DISPATCH}<br>
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @since 0.1.2
 */
public interface AuditProcessor {

	/**
	 * @param auditRef
	 *            String to audit reference, normally will be a business
	 *            reference.
	 * @param parameters
	 *            Parameters to be added to audit data. Each one must be
	 *            identified with {@link Audited} annotation. If not, the
	 *            parameter will be ignored. AuditException
	 * @throws AuditException
	 *             In case of error on audit process. Occurs only in the case of
	 *             {@link Constants#PATH_CONF_INTERRUPT_ON_ERROR} is tuned on in
	 *             {@link Constants#CONFIGURATION_FILE}.
	 */
	void doAudit(String auditRef, Object... parameters) throws AuditException;

	/**
	 * @param auditRef
	 *            String to audit reference, normally will be a business
	 *            reference.
	 * @param parameters
	 *            Parameter array to be added in audit data.
	 * @param parameterAnnotations
	 *            Matrix where second dimension contains a annotation array with
	 *            one {@link Audited} configuration to a parameter in equivalent
	 *            index on "parameters" array.
	 *
	 * @throws AuditException
	 *             In case of error on audit process. Occurs only in the case of
	 *             {@link Constants#PATH_CONF_INTERRUPT_ON_ERROR} is tuned on in
	 *             {@link Constants#CONFIGURATION_FILE}.
	 */
	void doAudit(String auditRef, Object[] parameters, Annotation[][] parameterAnnotations)
	    throws AuditException;

}