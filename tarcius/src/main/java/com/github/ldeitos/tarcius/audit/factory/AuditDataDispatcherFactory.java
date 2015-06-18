package com.github.ldeitos.tarcius.audit.factory;

import static com.github.ldeitos.tarcius.configuration.Configuration.getConfiguration;

import javax.enterprise.context.ApplicationScoped;

import com.github.ldeitos.tarcius.api.AuditDataDispatcher;
import com.github.ldeitos.tarcius.exception.InvalidConfigurationException;

@ApplicationScoped
public class AuditDataDispatcherFactory {

	public AuditDataDispatcher<?> getCurrentDispatcher() throws InvalidConfigurationException {
		return getConfiguration().getAuditDataDispatcher();
	}
}
