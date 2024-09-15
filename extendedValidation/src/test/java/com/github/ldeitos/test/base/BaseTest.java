package com.github.ldeitos.test.base;

import jakarta.inject.Inject;
import jakarta.validation.Validator;

import io.github.cdiunit.AdditionalClasses;
import io.github.cdiunit.junit4.CdiRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.github.ldeitos.qualifier.ExtendedValidator;
import com.github.ldeitos.validation.impl.ValidatorImpl;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.interpolator.MultipleBundlesSource;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ValidatorImpl.class, MultipleBundlesSource.class,
	ConfigInfoProvider.class })
public abstract class BaseTest {

	@Inject
	@ExtendedValidator
	private Validator validador;

	@Inject
	@ExtendedValidator
	private com.github.ldeitos.validation.Validator extendedValidator;

	@BeforeClass
	public static void setup() {
		System.setProperty("org.jboss.weld.nonPortableMode", "true");
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "off");
	}

	@AfterClass
	public static void shutdown() {
		System.clearProperty("org.jboss.weld.nonPortableMode");
	}

	protected Class<?> getClassOnTest() {
		return getClass();
	}

	public Validator getValidador() {
		return validador;
	}

	public com.github.ldeitos.validation.Validator getExtendedValidator() {
		return extendedValidator;
	}
}