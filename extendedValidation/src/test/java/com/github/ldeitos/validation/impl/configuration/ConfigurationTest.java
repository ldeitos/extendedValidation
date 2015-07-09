package com.github.ldeitos.validation.impl.configuration;

import static com.github.ldeitos.constants.Constants.MESSAGE_FILES_SYSTEM_PROPERTY;
import static com.github.ldeitos.validation.impl.configuration.Configuration.getConfiguration;
import static java.lang.System.setProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.ldeitos.util.ManualContext;
import com.github.ldeitos.validation.impl.interpolator.TestMessageSource;

@RunWith(CdiRunner.class)
@AdditionalClasses({ TestMessageSource.class, ManualContext.class })
public class ConfigurationTest {
	private static final String TEST_MESSAGE_FILE = "TestValidationMessage";

	@Test
	public void testConfigurationWithMessageFilesCofiguredByEnvironment() {
		setProperty(MESSAGE_FILES_SYSTEM_PROPERTY, "arq1, arq2");

		assertEquals(TestMessageSource.class, getConfiguration().getConfiguredMessagesSource().getClass());

		assertEquals(3, getConfiguration().getConfituredMessageFiles().size());
		assertTrue(getConfiguration().getConfituredMessageFiles().contains(TEST_MESSAGE_FILE));
		assertTrue(getConfiguration().getConfituredMessageFiles().contains("arq1"));
		assertTrue(getConfiguration().getConfituredMessageFiles().contains("arq2"));

		System.clearProperty(MESSAGE_FILES_SYSTEM_PROPERTY);
	}

	@Test
	public void testConfigurationWithoutMessageFilesCofiguredByEnvironment() {

		assertEquals(TestMessageSource.class, getConfiguration().getConfiguredMessagesSource().getClass());

		assertEquals(1, getConfiguration().getConfituredMessageFiles().size());
		assertTrue(getConfiguration().getConfituredMessageFiles().contains(TEST_MESSAGE_FILE));
	}
}
