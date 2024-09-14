package com.github.ldeitos.validator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jakarta.enterprise.inject.Produces;
import jakarta.validation.ConstraintViolation;

import io.github.cdiunit.AdditionalClasses;
import io.github.cdiunit.ProducesAlternative;
import org.junit.Ignore;
import org.junit.Test;

import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.test.base.stubs.FormattedPresentationValidatorImpl;
import com.github.ldeitos.test.base.stubs.FormattedTest;
import com.github.ldeitos.test.base.stubs.GrupoTestBeanA;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.interpolator.PreInterpolator;

@AdditionalClasses({ PreInterpolator.class, FormattedPresentationValidatorImpl.class })
@Ignore
public class FormattedOutputMessageTest extends BaseTest {
	@Produces
	@ProducesAlternative
	private ConfigInfoProvider cip = new ConfigInfoProvider() {
		@Override
		public String getConfigFileName() {
			return "extendedValidation_configuredTemplateMessagePresentation.xml";
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

	@Test
	public void testFormatedOutputWithExtendedAbstractValidator() {
		FormattedTest f = new FormattedTest();

		Set<ConstraintViolation<FormattedTest>> violations = getValidador().validate(f, GrupoTestBeanA.class);
		assertEquals(2, violations.size());
		List<String> msgs = new ArrayList<String>();

		Iterator<ConstraintViolation<FormattedTest>> iterator = violations.iterator();
		msgs.add(iterator.next().getMessage());
		msgs.add(iterator.next().getMessage());
		assertEquals(true, msgs.contains("(KEY) Message Test"));
		assertEquals(true, msgs.contains("(KEYB) Test B Message"));
	}
}