package com.github.ldeitos.validator;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.validation.ConstraintViolation;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.ProducesAlternative;
import org.junit.Test;

import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.test.base.stubs.FormattedTest;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;

@AdditionalClasses({})
public class FormattedOutputMessageTest extends BaseTest {
	@Produces
	@ProducesAlternative
	private ConfigInfoProvider cip = new ConfigInfoProvider() {
		@Override
		public String getConfigFileName() {
			return "META-INF/extendedValidation_configuredTemplateMessagePresentation.xml";
		}

		@Override
		protected boolean isInTest() {
			return true;
		}
	};

	@Test
	public void testFormatedOutput() {
		FormattedTest f = new FormattedTest();

		Set<ConstraintViolation<FormattedTest>> violations = getValidador().validate(f);
		assertEquals(1, violations.size());
		String msg = violations.iterator().next().getMessage();
		assertEquals("(KEY) Message Test", msg);
	}
}
