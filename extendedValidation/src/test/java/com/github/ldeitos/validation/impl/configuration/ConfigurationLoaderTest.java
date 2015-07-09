package com.github.ldeitos.validation.impl.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.github.ldeitos.validation.impl.configuration.dto.ConfigurationDTO;

public class ConfigurationLoaderTest {
	
	private static final String TEST_MESSAGE_SOURCE = "com.github.ldeitos.validation.impl.interpolator.TestMessageSource";
	
	private static final String TEST_MESSAGE_FILE = "TestValidationMessage";

	@Test
	public void testLoadExtendedValidatonXML(){
		ConfigurationDTO dto = ConfigurationLoader.loadConfiguration();
		
		assertEquals(TEST_MESSAGE_SOURCE, dto.getMessageSource());
		assertFalse(dto.getMessageFiles().isEmpty());
		assertEquals(TEST_MESSAGE_FILE, dto.getMessageFiles().get(0).getMessageFile());
	}
	
}
