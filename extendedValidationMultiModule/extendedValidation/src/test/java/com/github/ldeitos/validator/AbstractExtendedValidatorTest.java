package com.github.ldeitos.validator;

import static com.github.ldeitos.test.base.GeneralTestConfiguration.ENABLE_REAL_IMPLEMETATION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.ArrayUtils;
import org.jglue.cdiunit.AdditionalClasses;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.validation.impl.interpolator.TestMessageSource;
import com.github.ldeitos.validator.stubs.Bean;
import com.github.ldeitos.validator.stubs.PathBean;
import com.github.ldeitos.validator.stubs.PathTestValidatorImpl;
import com.github.ldeitos.validator.stubs.TestValidatorImpl;

@AdditionalClasses({ TestMessageSource.class, PathTestValidatorImpl.class, TestValidatorImpl.class })
public class AbstractExtendedValidatorTest extends BaseTest {

	@BeforeClass
	public static void init() {
		ENABLE_REAL_IMPLEMETATION = true;
	}

	@AfterClass
	public static void shutdown() {
		ENABLE_REAL_IMPLEMETATION = false;
	}

	@Test
	public void testAddViolationWithDefaultTemplate() {
		String expected = "NotNull defaultConstraintParameter";
		Bean bean = new Bean();
		Set<ConstraintViolation<Bean>> violations = getValidador().validate(bean);

		assertEquals(1, violations.size());
		ConstraintViolation<Bean> violation = violations.iterator().next();
		assertEquals(expected, violation.getMessage());
	}

	@Test
	public void testAddViolationNotDefaultTemplateAndParametrizedByViolationRegAndConstraintFild() {
		String expected = "Not Default constraint: parameter1, parameter2, valueParametrized";
		Bean bean = new Bean();

		bean.setStringField("");
		bean.setBooleanField(true);

		Set<ConstraintViolation<Bean>> violations = getValidador().validate(bean);

		assertEquals(1, violations.size());
		ConstraintViolation<Bean> violation = violations.iterator().next();
		assertEquals(expected, violation.getMessage());
	}

	@Test
	public void testAddViolationNotDefaultTemplateIndexedParametersTemplate() {
		String expected = "Test Indexed parmeter test with parameters: Test, param2, param3";
		Bean bean = new Bean();

		bean.setStringField("");
		bean.setOtherBooleanField(true);

		Set<ConstraintViolation<Bean>> violations = getValidador().validate(bean);

		assertEquals(1, violations.size());
		ConstraintViolation<Bean> violation = violations.iterator().next();
		assertEquals(expected, violation.getMessage());
	}

	@Test
	public void testAddMultimpleViolation() {
		String[] expected = { "Not Default constraint: parameter1, parameter2, valueParametrized",
			"Test Indexed parmeter test with parameters: Test, param2, param3",
		"NotNull defaultConstraintParameter" };
		Bean bean = new Bean();

		bean.setBooleanField(true);
		bean.setOtherBooleanField(true);

		Set<ConstraintViolation<Bean>> violations = getValidador().validate(bean);

		assertEquals(3, violations.size());
		Iterator<ConstraintViolation<Bean>> iterator = violations.iterator();
		ConstraintViolation<Bean> violation1 = iterator.next();
		ConstraintViolation<Bean> violation2 = iterator.next();
		ConstraintViolation<Bean> violation3 = iterator.next();

		assertTrue(ArrayUtils.contains(expected, violation1.getMessage()));
		assertTrue(ArrayUtils.contains(expected, violation2.getMessage()));
		assertTrue(ArrayUtils.contains(expected, violation3.getMessage()));
	}

	@Test
	public void testAddViolationWithDefaultTemplateAndPathSimpleField() {
		String expected = "NotNull defaultConstraintParameter";
		PathBean bean = new PathBean();
		Set<ConstraintViolation<PathBean>> violations = getValidador().validate(bean);

		assertEquals(1, violations.size());
		ConstraintViolation<PathBean> violation = violations.iterator().next();
		assertEquals(expected, violation.getMessage());
	}

}
