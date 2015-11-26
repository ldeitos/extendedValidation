package com.github.ldeitos.validation.impl.configuration;

import static com.github.ldeitos.constants.Constants.CONFIGURATION_FILE;

import javax.inject.Singleton;

/**
 * Unit test cases use only. Provide configuration file name and a "inTest"
 * flag.
 *
 * @author <a href="mailto:leandro.deitos@gmail.com">Leandro Deitos</a>
 *
 * @since 1.0.RC2
 */
@Singleton
public class ConfigInfo {

	public String getConfigFileName() {
		return CONFIGURATION_FILE;
	}

	protected boolean isInTest() {
		return false;
	}
}
