package com.github.ldeitos.tarcius.api;

public interface AuditDataDispatcher<AD> {
	void dispatch(AD auditData);
}
