package com.github.ldeitos.tarcius.api;

/**
 * Audit data container. Audit data model must be defined by component user.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @param <AD>
 *            Type of audit data model defined by component user.
 */
public class AuditDataContainer<AD> {
	private AD auditData;

	public AuditDataContainer(AD auditData) {
		this.auditData = auditData;
	}

	public AD getAuditData() {
		return auditData;
	}
}
