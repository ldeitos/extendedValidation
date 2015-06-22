package com.github.ldeitos.tarcius.configuration;

/**
 * Defines auditing process phases.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public enum AuditPhases {
	/**
	 * Phase to identify parameters to be audited.
	 */
	AUDITED_PARAMETER_DISCOVERY,
	/**
	 * Phase to resolve, or translate, parameters to audit format
	 */
	AUDITED_PARAMETER_RESOLVE,
	/**
	 * Phase to format audit data.
	 */
	AUDIT_FORMAT,
	/**
	 * Phase to dispatch audit data to destination.
	 */
	AUDIT_DISPATCH;
}
