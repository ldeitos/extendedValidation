package com.github.ldeitos.tarcius.configuration;

import static com.github.ldeitos.tarcius.configuration.Constants.CONFIGURATION_FILE;

import javax.inject.Singleton;

@Singleton
public class ConfigInfoProvider {

	public String getConfigFileName() {
		return CONFIGURATION_FILE;
	}

	protected boolean isInTest() {
		return false;
	}
}
