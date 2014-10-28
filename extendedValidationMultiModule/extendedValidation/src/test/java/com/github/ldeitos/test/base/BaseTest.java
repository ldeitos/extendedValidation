package com.github.ldeitos.test.base;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.github.ldeitos.util.ManualContext;
import com.github.ldeitos.validation.impl.ValidatorImpl;
import com.github.ldeitos.validation.impl.interpolator.MultipleBundlesSource;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ValidatorImpl.class, MultipleBundlesSource.class, ManualContext.class })
public abstract class BaseTest {

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
		return BaseTest.class;
	}

	// @Rule
	// public TestLoggerFactoryResetRule resetLogRule() {
	// return testLoggerFactoryResetRule;
	// }
}
