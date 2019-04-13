package com.github.ldeitos.validation.impl.configuration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.ldeitos.validation.impl.configuration.dto.ConfigurationDTO;

public class ConfigurationLoaderTest {

	private static final String TEST_MESSAGE_SOURCE = "com.github.ldeitos.validation.impl.interpolator.TestMessageSource";

	private static final String TEST_MESSAGE_FILE_0 = "TestValidationMessage";
	
	private static final String TEST_MESSAGE_FILE_1 = "OtherTestValidationMessage";

	@Test
	public void testLoadExtendedValidatonXML() {
		ConfigurationDTO dto = ConfigurationLoader.loadConfiguration(new ConfigInfoProvider());

		assertEquals(TEST_MESSAGE_SOURCE, dto.getMessageSource());
		assertEquals(2, dto.getMessageFiles().size());
		assertEquals(TEST_MESSAGE_FILE_0, dto.getMessageFiles().get(0).getMessageFile());
		assertEquals(TEST_MESSAGE_FILE_1, dto.getMessageFiles().get(1).getMessageFile());
	}

}