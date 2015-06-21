package com.github.ldeitos.tarcius.audit.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.ldeitos.tarcius.api.AuditDataDispatcher;
import com.github.ldeitos.tarcius.configuration.Configuration;
import com.github.ldeitos.tarcius.configuration.ConfigurationProvider;
import com.github.ldeitos.tarcius.exception.InvalidConfigurationException;

@ApplicationScoped
public class AuditDataDispatcherFactory {

	@Inject
	private ConfigurationProvider configFileNameProvider;

	public AuditDataDispatcher<?> getCurrentDispatcher() throws InvalidConfigurationException {
		return getConfiguration().getAuditDataDispatcher();
	}

	private Configuration getConfiguration() {
		return Configuration.getConfiguration(configFileNameProvider);
	}
}
