package com.github.ldeitos.validation;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.github.ldeitos.util.ManualContext;
import com.github.ldeitos.validation.impl.MessageResolverImpl;
import com.github.ldeitos.validation.impl.ValidatorImpl;
import com.github.ldeitos.validation.impl.interceptor.ValidateParametersInterceptor;
import com.github.ldeitos.validation.impl.interpolator.MultipleBundlesSource;
import com.github.ldeitos.validation.impl.util.DefaultValidationClosure;
import com.github.ldeitos.validators.DigitsValidatorImpl;
import com.github.ldeitos.validators.EmptyValidatorImpl;
import com.github.ldeitos.validators.MaxDecimalValidatorImpl;
import com.github.ldeitos.validators.MaxValidatorImpl;
import com.github.ldeitos.validators.MinDecimalValidatorImpl;
import com.github.ldeitos.validators.MinValidatorImpl;
import com.github.ldeitos.validators.NotEmptyValidatorImpl;
import com.github.ldeitos.validators.PatternValidatorImpl;
import com.github.ldeitos.validators.RangeValidatorImpl;
import com.github.ldeitos.validators.SizeValidatorImpl;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ValidatorImpl.class, MultipleBundlesSource.class, ManualContext.class,
    NotEmptyValidatorImpl.class, EmptyValidatorImpl.class, PatternValidatorImpl.class,
    DigitsValidatorImpl.class, SizeValidatorImpl.class, MinDecimalValidatorImpl.class,
    MinValidatorImpl.class, MaxDecimalValidatorImpl.class, MaxValidatorImpl.class, RangeValidatorImpl.class,
    ValidateParametersInterceptor.class, DefaultValidationClosure.class, MessageResolverImpl.class })
public class ExtendedValidationBaseTest {

	@BeforeClass
	public static void setup() {
		System.setProperty("org.jboss.weld.nonPortableMode", "true");
	}

	@AfterClass
	public static void shutdown() {
		System.clearProperty("org.jboss.weld.nonPortableMode");
	}

}
