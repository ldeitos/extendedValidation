package com.github.ldeitos.tarcius.audit.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.ldeitos.tarcius.api.AuditDataDispatcher;
import com.github.ldeitos.tarcius.configuration.ConfigInfoProvider;
import com.github.ldeitos.tarcius.configuration.Configuration;
import com.github.ldeitos.tarcius.exception.InvalidConfigurationException;

@ApplicationScoped
public class AuditDataDispatcherFactory {

	@Inject
	private ConfigInfoProvider configInfoProvider;

	public AuditDataDispatcher<?> getCurrentDispatcher() throws InvalidConfigurationException {
		return getConfiguration().getAuditDataDispatcher();
	}

	private Configuration getConfiguration() throws InvalidConfigurationException {
		return Configuration.getConfiguration(configInfoProvider);
	}
}
