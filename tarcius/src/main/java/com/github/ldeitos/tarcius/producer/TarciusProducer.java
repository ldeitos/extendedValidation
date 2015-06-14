package com.github.ldeitos.tarcius.producer;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

import com.github.ldeitos.tarcius.audit.AuditContext;

public class TarciusProducer {

	@Produces
	@Default
	public AuditContext producesAuditContext() {
		return new AuditContext();
	}
}
