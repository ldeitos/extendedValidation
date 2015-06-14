package com.github.ldeitos.tarcius.audit.factory;

import javax.enterprise.context.ApplicationScoped;

import com.github.ldeitos.tarcius.api.AuditDataFormatter;

@ApplicationScoped
public class AuditDataFormatterFactory {

	public AuditDataFormatter<?> getCurrentFormatter() {
		return null;
	}
}
