package com.github.ldeitos.validation;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.github.ldeitos.validation.impl.ValidatorImpl;
import com.github.ldeitos.validation.impl.interpolator.MultipleBundlesSource;

@RunWith(CdiRunner.class)
@AdditionalClasses({ValidatorImpl.class, MultipleBundlesSource.class})
public class ExtendedValidationBaseTest {
	
	@BeforeClass
	public static void setup(){
		System.setProperty("org.jboss.weld.nonPortableMode", "true");
	}
	
	@AfterClass
	public static void shutdown(){
		System.clearProperty("org.jboss.weld.nonPortableMode");
	}

}
