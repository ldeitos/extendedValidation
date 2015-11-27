package com.github.ldeitos.validator;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.validation.ConstraintViolation;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.ProducesAlternative;
import org.junit.Test;

import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.test.base.stubs.TestBeanC;
import com.github.ldeitos.test.base.stubs.TestBeanD;
import com.github.ldeitos.test.base.stubs.TestBeanE;
import com.github.ldeitos.test.base.stubs.TestBeanF;
import com.github.ldeitos.validation.impl.configuration.ConfigInfo;
import com.github.ldeitos.validation.impl.interpolator.PreInterpolator;

@AdditionalClasses({ PreInterpolator.class })
public class ParametrizedMessageParameterTest extends BaseTest {
	@Produces
	@ProducesAlternative
	private ConfigInfo cip = new ConfigInfo() {
		@Override
		public String getConfigFileName() {
			return "META-INF/extendedValidation_parametrizedMessageParameter.xml";
		}

		@Override
		protected boolean isInTest() {
			return true;
		}
	};

	@Test
	public void testParametrizedIndexedMessageParameter() {
		TestBeanC f = new TestBeanC();

		Set<ConstraintViolation<TestBeanC>> violations = getValidador().validate(f);
		assertEquals(1, violations.size());
		String msg = violations.iterator().next().getMessage();
		assertEquals("Parametrized indexed parameter message", msg);
	}

	@Test
	public void testParametrizedNominalMessageParameter() {
		TestBeanD f = new TestBeanD();

		Set<ConstraintViolation<TestBeanD>> violations = getValidador().validate(f);
		assertEquals(1, violations.size());
		String msg = violations.iterator().next().getMessage();
		assertEquals("Parametrized nomimal parameter message", msg);
	}

	@Test
	public void testNormalIndexedMessageParameter() {
		TestBeanE f = new TestBeanE();

		Set<ConstraintViolation<TestBeanE>> violations = getValidador().validate(f);
		assertEquals(1, violations.size());
		String msg = violations.iterator().next().getMessage();
		assertEquals("Parametrized indexed parameter message", msg);
	}

	@Test
	public void testNormalNominalMessageParameter() {
		TestBeanF f = new TestBeanF();

		Set<ConstraintViolation<TestBeanF>> violations = getValidador().validate(f);
		assertEquals(1, violations.size());
		String msg = violations.iterator().next().getMessage();
		assertEquals("Parametrized nomimal parameter message", msg);
	}

}
