package com.github.ldeitos.tarcius.api;

import com.github.ldeitos.tarcius.audit.AuditDataSource;

public interface AuditDataFormatter<AD> {
	AuditDataContainer<AD> format(AuditDataSource preAuditData);
}
