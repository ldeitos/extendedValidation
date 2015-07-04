package com.github.ldeitos.tarcius;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.ldeitos.tarcius.configuration.ConfigInfoProvider;
import com.github.ldeitos.tarcius.configuration.Configuration;
import com.github.ldeitos.tarcius.configuration.factory.TarciusComponentFactory;
import com.github.ldeitos.tarcius.exception.InvalidConfigurationException;
import com.github.ldeitos.util.ManualContext;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ManualContext.class, TarciusComponentFactory.class })
public class ConfigurationLoadWithoutCDITest {

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
