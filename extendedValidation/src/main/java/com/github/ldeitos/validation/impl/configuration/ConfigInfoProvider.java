package com.github.ldeitos.validation.impl.configuration;

import static com.github.ldeitos.constants.Constants.CONFIGURATION_FILE;
import static com.github.ldeitos.constants.Constants.CONFIGURATION_PATH;

import javax.inject.Singleton;

@Singleton
public class ConfigInfoProvider {

	public String getConfigFileName() {
		return CONFIGURATION_FILE;
	}
	
	public String getConfigPath() {
		return CONFIGURATION_PATH;
	}

	protected boolean isInTest() {
		return false;
	}
}