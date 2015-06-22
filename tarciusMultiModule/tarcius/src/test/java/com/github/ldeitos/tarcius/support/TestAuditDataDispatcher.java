package com.github.ldeitos.tarcius.support;

import com.github.ldeitos.tarcius.api.AuditDataDispatcher;

public class TestAuditDataDispatcher implements AuditDataDispatcher<AuditData> {

	@Override
	public void dispatch(AuditData auditData) {
		MessageDestination.setMessage(auditData.getStringMessage());
	}

}
