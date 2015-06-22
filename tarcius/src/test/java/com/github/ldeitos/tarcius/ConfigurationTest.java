package com.github.ldeitos.tarcius;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Test;

import com.github.ldeitos.tarcius.configuration.ConfigInfoProvider;
import com.github.ldeitos.tarcius.configuration.Configuration;
import com.github.ldeitos.tarcius.exception.InvalidConfigurationException;

public class ConfigurationTest {

	@AfterClass
	public static void shutdown() {
		Configuration.reset();
	}

	@Test
	public void testValidConfiguration() throws InvalidConfigurationException {
		Configuration conf = Configuration.getConfiguration(getConfigInfoProvider("META-INF/tarcius.xml"));
		assertFalse(conf.mustInterruptOnError());
		assertNotNull(conf.getAuditDataDispatcher());
		assertNotNull(conf.getAuditDataFormatter());
	}

	@Test
	public void testConfigurationInterruptOnError() throws InvalidConfigurationException {
		Configuration conf = Configuration
		    .getConfiguration(getConfigInfoProvider("META-INF/tarcius_interruptonerror.xml"));
		assertTrue(conf.mustInterruptOnError());
	}

	@Test(expected = InvalidConfigurationException.class)
	public void testConfigurationFileNotExist() throws InvalidConfigurationException {
		Configuration.getConfiguration(getConfigInfoProvider("foo.xml"));
	}

	@Test(expected = InvalidConfigurationException.class)
	public void testConfigurationFileWithoutDispatcherConf() throws InvalidConfigurationException {
		Configuration.getConfiguration(getConfigInfoProvider("META-INF/tarcius_withoutdispatcher.xml"));
	}

	@Test(expected = InvalidConfigurationException.class)
	public void testConfigurationFileWithoutFormatterConf() throws InvalidConfigurationException {
		Configuration.getConfiguration(getConfigInfoProvider("META-INF/tarcius_withoutformatter.xml"));
	}

	@Test(expected = InvalidConfigurationException.class)
	public void testConfigurationInvalidFormatter() throws InvalidConfigurationException {
		Configuration conf = Configuration
		    .getConfiguration(getConfigInfoProvider("META-INF/tarcius_invalidformatter.xml"));
		conf.getAuditDataFormatter();
	}

	@Test(expected = InvalidConfigurationException.class)
	public void testConfigurationInvalidDispatcher() throws InvalidConfigurationException {
		Configuration conf = Configuration
		    .getConfiguration(getConfigInfoProvider("META-INF/tarcius_invaliddispatcher.xml"));
		conf.getAuditDataDispatcher();
	}

	public ConfigInfoProvider getConfigInfoProvider(final String fileName) {
		return new ConfigInfoProvider() {
			@Override
			public String getConfigFileName() {
				return fileName;
			};

			@Override
			protected boolean isInTest() {
				return true;
			};
		};
	}

}
