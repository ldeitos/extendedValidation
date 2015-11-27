package com.github.ldeitos.validation.impl.configuration;

import static com.github.ldeitos.constants.Constants.CONFIGURATION_FILE;

import javax.inject.Singleton;

@Singleton
public class ConfigInfo {

	public String getConfigFileName() {
		return CONFIGURATION_FILE;
	}

	protected boolean isInTest() {
		return false;
	}
}
