package com.github.ldeitos.validation.impl.configuration;

import static com.github.ldeitos.constants.Constants.MESSAGE_FILES_SYSTEM_PROPERTY;
import static com.github.ldeitos.validation.impl.configuration.Configuration.getConfiguration;
import static java.lang.System.setProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.enterprise.inject.Produces;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.ProducesAlternative;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.ldeitos.validation.impl.interpolator.TestMessageSource;

@RunWith(CdiRunner.class)
@AdditionalClasses({ TestMessageSource.class })
public class ConfigurationTest {

	private static final String TEST_MESSAGE_FILE = "TestValidationMessage";
	
	private static final String OTHER_TEST_MESSAGE_FILE = "OtherTestValidationMessage";

	@Produces
	@ProducesAlternative
	private  ConfigInfoProvider configProvider = new ConfigInfoProvider();

	private Configuration configuration = getConfiguration(configProvider);

	@Test
	public void testConfigurationWithMessageFilesCofiguredByEnvironment() {
		setProperty(MESSAGE_FILES_SYSTEM_PROPERTY, "arq1, arq2");

		assertEquals(TestMessageSource.class, configuration.getConfiguredMessagesSource().getClass());

		assertEquals(4, configuration.getConfituredMessageFiles().size());
		assertTrue(configuration.getConfituredMessageFiles().contains(TEST_MESSAGE_FILE));
		assertTrue(configuration.getConfituredMessageFiles().contains(OTHER_TEST_MESSAGE_FILE));
		assertTrue(configuration.getConfituredMessageFiles().contains("arq1"));
		assertTrue(configuration.getConfituredMessageFiles().contains("arq2"));

		System.clearProperty(MESSAGE_FILES_SYSTEM_PROPERTY);
	}

	@Test
	public void testConfigurationWithoutMessageFilesCofiguredByEnvironment() {

		assertEquals(TestMessageSource.class, configuration.getConfiguredMessagesSource().getClass());

		assertEquals(2, configuration.getConfituredMessageFiles().size());
		assertTrue(configuration.getConfituredMessageFiles().contains(TEST_MESSAGE_FILE));
		assertTrue(configuration.getConfituredMessageFiles().contains(OTHER_TEST_MESSAGE_FILE));
	}
}