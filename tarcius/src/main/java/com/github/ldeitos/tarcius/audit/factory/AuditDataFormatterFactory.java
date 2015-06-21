package com.github.ldeitos.tarcius.audit.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.ldeitos.tarcius.api.AuditDataFormatter;
import com.github.ldeitos.tarcius.configuration.Configuration;
import com.github.ldeitos.tarcius.configuration.ConfigurationProvider;
import com.github.ldeitos.tarcius.exception.InvalidConfigurationException;

@ApplicationScoped
public class AuditDataFormatterFactory {

	@Inject
	private ConfigurationProvider configFileNameProvider;

	public AuditDataFormatter<?> getCurrentFormatter() throws InvalidConfigurationException {
		return getConfiguration().getAuditDataFormatter();
	}

	private Configuration getConfiguration() {
		return Configuration.getConfiguration(configFileNameProvider);
	}
}
