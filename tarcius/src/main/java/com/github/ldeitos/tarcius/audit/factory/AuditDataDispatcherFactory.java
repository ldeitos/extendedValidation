package com.github.ldeitos.tarcius.audit.factory;

import javax.enterprise.context.ApplicationScoped;

import com.github.ldeitos.tarcius.api.AuditDataDispatcher;

@ApplicationScoped
public class AuditDataDispatcherFactory {

	public AuditDataDispatcher<?> getCurrentDispatcher() {
		return null;
	}
}
