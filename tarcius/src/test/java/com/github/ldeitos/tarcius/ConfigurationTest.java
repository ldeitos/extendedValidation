package com.github.ldeitos.tarcius;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.ldeitos.tarcius.configuration.ConfigInfoProvider;
import com.github.ldeitos.tarcius.configuration.Configuration;
import com.github.ldeitos.tarcius.configuration.factory.TarciusComponentFactory;
import com.github.ldeitos.tarcius.exception.InvalidConfigurationException;
import com.github.ldeitos.tarcius.support.TestAuditDataDispatcher;
import com.github.ldeitos.tarcius.support.TestAuditDataFormatter;
import com.github.ldeitos.util.ManualContext;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ManualContext.class, TestAuditDataDispatcher.class, TestAuditDataFormatter.class,
	TarciusComponentFactory.class })
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

	@Test
	public void testConfigurationFileNotExist() throws InvalidConfigurationException {
		Configuration conf = Configuration.getConfiguration(getConfigInfoProvider("foo.xml"));
		assertFalse(conf.mustInterruptOnError());
		assertNotNull(conf.getAuditDataDispatcher());
		assertNotNull(conf.getAuditDataFormatter());
	}

	@Test
	public void testConfigurationFileEmpty() throws InvalidConfigurationException {
		Configuration conf = Configuration
			.getConfiguration(getConfigInfoProvider("META-INF/empty_tarcius.xml"));
		assertFalse(conf.mustInterruptOnError());
		assertNotNull(conf.getAuditDataDispatcher());
		assertNotNull(conf.getAuditDataFormatter());
	}

	@Test
	public void testConfigurationFileWithoutDispatcherConf() throws InvalidConfigurationException {
		Configuration conf = Configuration
		    .getConfiguration(getConfigInfoProvider("META-INF/tarcius_withoutdispatcher.xml"));
		assertFalse(conf.mustInterruptOnError());
		assertNotNull(conf.getAuditDataDispatcher());
		assertNotNull(conf.getAuditDataFormatter());
	}

	@Test
	public void testConfigurationFileWithoutFormatterConf() throws InvalidConfigurationException {
		Configuration conf = Configuration
		    .getConfiguration(getConfigInfoProvider("META-INF/tarcius_withoutformatter.xml"));
		assertFalse(conf.mustInterruptOnError());
		assertNotNull(conf.getAuditDataDispatcher());
		assertNotNull(conf.getAuditDataFormatter());
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
