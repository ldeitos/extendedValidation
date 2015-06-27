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
import com.github.ldeitos.validator.stubs.Address;
import com.github.ldeitos.validator.stubs.AddressValidatorDefaultImpl;
import com.github.ldeitos.validator.stubs.AddressValidatorExtendedImpl;
import com.github.ldeitos.validator.stubs.Bean;
import com.github.ldeitos.validator.stubs.GrupoAddressClassLevelDefault;
import com.github.ldeitos.validator.stubs.GrupoAddressClassLevelExtended;
import com.github.ldeitos.validator.stubs.GrupoClassLevelUserDefault;
import com.github.ldeitos.validator.stubs.GrupoClassLevelUserExtended;
import com.github.ldeitos.validator.stubs.GrupoPropertyLevelDefault;
import com.github.ldeitos.validator.stubs.GrupoPropertyLevelExtended;
import com.github.ldeitos.validator.stubs.GrupoPropertyLevelMappedDefault;
import com.github.ldeitos.validator.stubs.GrupoPropertyLevelMappedExtended;
import com.github.ldeitos.validator.stubs.PathTestValidatorImpl;
import com.github.ldeitos.validator.stubs.PropertyMappedValidatorDefaultImpl;
import com.github.ldeitos.validator.stubs.PropertyMappedValidatorExtendedImpl;
import com.github.ldeitos.validator.stubs.PropertyValidatorDefaultImpl;
import com.github.ldeitos.validator.stubs.PropertyValidatorExtendedImpl;
import com.github.ldeitos.validator.stubs.TestValidatorImpl;
import com.github.ldeitos.validator.stubs.User;
import com.github.ldeitos.validator.stubs.UserValidatorDefaultImpl;
import com.github.ldeitos.validator.stubs.UserValidatorExtendedImpl;

@AdditionalClasses({ TestMessageSource.class, PathTestValidatorImpl.class, TestValidatorImpl.class,
    PropertyValidatorDefaultImpl.class, PropertyValidatorExtendedImpl.class,
	AddressValidatorDefaultImpl.class, AddressValidatorExtendedImpl.class,
    PropertyMappedValidatorDefaultImpl.class, PropertyMappedValidatorExtendedImpl.class,
    UserValidatorDefaultImpl.class, UserValidatorExtendedImpl.class })
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
	public void testAddViolationSimpleField() {
		User user = new User();

		Set<ConstraintViolation<User>> extendedConstraint = getValidador().validate(user,
			GrupoPropertyLevelExtended.class);
		Set<ConstraintViolation<User>> defaultConstraint = getValidador().validate(user,
			GrupoPropertyLevelDefault.class);

		ConstraintViolation<User> cDefault = defaultConstraint.iterator().next();
		ConstraintViolation<User> cExtended = extendedConstraint.iterator().next();

		assertEquals(cDefault.getPropertyPath(), cExtended.getPropertyPath());
	}

	@Test
	public void testAddViolationSimpleFieldMapped() {
		User user = new User();

		Set<ConstraintViolation<User>> extendedConstraint = getValidador().validate(user,
			GrupoPropertyLevelMappedExtended.class);
		Set<ConstraintViolation<User>> defaultConstraint = getValidador().validate(user,
			GrupoPropertyLevelMappedDefault.class);

		ConstraintViolation<User> cDefault = defaultConstraint.iterator().next();
		ConstraintViolation<User> cExtended = extendedConstraint.iterator().next();

		assertEquals(cDefault.getPropertyPath(), cExtended.getPropertyPath());
	}

	@Test
	public void testAddViolationClassLevelWithPath() {
		Address address = new Address();

		Set<ConstraintViolation<Address>> defaultConstraint = getValidador().validate(address,
			GrupoAddressClassLevelDefault.class);
		Set<ConstraintViolation<Address>> extendedConstraint = getValidador().validate(address,
			GrupoAddressClassLevelExtended.class);

		ConstraintViolation<Address> cDefault = defaultConstraint.iterator().next();
		ConstraintViolation<Address> cExtended = extendedConstraint.iterator().next();

		assertEquals(cDefault.getPropertyPath(), cExtended.getPropertyPath());
	}

	@Test
	public void testAddViolationClassLevelWithDeepMappedPath() {
		User user = new User();

		Set<ConstraintViolation<User>> defaultConstraint = getValidador().validate(user,
			GrupoClassLevelUserDefault.class);
		Set<ConstraintViolation<User>> extendedConstraint = getValidador().validate(user,
			GrupoClassLevelUserExtended.class);

		ConstraintViolation<User> cDefault = defaultConstraint.iterator().next();
		ConstraintViolation<User> cExtended = extendedConstraint.iterator().next();

		assertEquals(cDefault.getPropertyPath(), cExtended.getPropertyPath());
	}
}
