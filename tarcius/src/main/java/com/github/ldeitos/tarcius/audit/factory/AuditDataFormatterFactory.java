package com.github.ldeitos.tarcius.audit.factory;

import static com.github.ldeitos.tarcius.configuration.Configuration.getConfiguration;

import javax.enterprise.context.ApplicationScoped;

import com.github.ldeitos.tarcius.api.AuditDataFormatter;
import com.github.ldeitos.tarcius.exception.InvalidConfigurationException;

@ApplicationScoped
public class AuditDataFormatterFactory {

	public AuditDataFormatter<?> getCurrentFormatter() throws InvalidConfigurationException {
		return getConfiguration().getAuditDataForrmatter();
	}
}
