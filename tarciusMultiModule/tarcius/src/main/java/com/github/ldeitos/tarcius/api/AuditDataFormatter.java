package com.github.ldeitos.tarcius.api;

import com.github.ldeitos.tarcius.audit.AuditDataSource;

/**
 * Interface to audit data formatter. This element is responsible for final
 * audit message formatting.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @param <AD>
 *            Type of object that will contain the data to send of the audit
 *            destination.
 */
public interface AuditDataFormatter<AD> {
	/**
	 * @param preAuditData
	 *            {@link AuditDataSource} instance.
	 * @return {@link AuditDataContainer} containing audit data.
	 */
	AuditDataContainer<AD> format(AuditDataSource preAuditData);
}
