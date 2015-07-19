package com.github.ldeitos.validation.impl.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.ProducesAlternative;
import org.junit.Test;

import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.test.base.stubs.TestBeanA;
import com.github.ldeitos.test.base.stubs.TestBeanB;
import com.github.ldeitos.test.base.stubs.TestCustomValidationClosure;
import com.github.ldeitos.test.base.stubs.TestInterceptedClassValidation;
import com.github.ldeitos.test.base.stubs.TestInterceptedMethodsValidation;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.interpolator.TestMessageSource;

@AdditionalClasses({ ValidateParametersInterceptor.class, TestInterceptedClassValidation.class,
    TestInterceptedMethodsValidation.class, TestMessageSource.class, TestCustomValidationClosure.class })
public class ValidateParametersInterceptorWithConfiguredValidationClosureTest extends BaseTest {

	@Produces
	@ProducesAlternative
	private ConfigInfoProvider cip = new ConfigInfoProvider() {
		@Override
		public String getConfigFileName() {
			return "META-INF/extendedValidation_configuredValidationClosure.xml";
		}

		@Override
		protected boolean isInTest() {
			return true;
		}
	};

	@Inject
	private TestInterceptedClassValidation classIntercepted;

	@Inject
	private TestInterceptedMethodsValidation methodIntercepted;

	private TestBeanA validBeanA = new TestBeanA("");

	private TestBeanA invalidBeanA = new TestBeanA();

	private TestBeanB validBeanB = new TestBeanB("");

	private TestBeanB invalidBeanB = new TestBeanB();

	@Test
	public void testMethodInterceptedInvockedMethodNoParameter() {
		methodIntercepted.noParameters();
	}

	@Test
	public void testClassInterceptedInvockedMethodNoParameter() {
		classIntercepted.noParameters();
	}

	@Test
	public void testMethodInterceptedValidParameterMethodOneParameter() {
		methodIntercepted.oneParameter(validBeanA);
	}

	@Test
	public void testClassInterceptedValidParameterMethodOneParameter() {
		classIntercepted.oneParameter(validBeanA);
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodOneParameter() {
		try {
			methodIntercepted.oneParameter(invalidBeanA);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodOneParameter() {
		try {
			classIntercepted.oneParameter(invalidBeanA);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}

	@Test
	public void testMethodInterceptedValidParameterMethodTwoParameter() {
		methodIntercepted.twoParameter(validBeanA, validBeanB);
	}

	@Test
	public void testClassInterceptedValidParameterMethodTwoParameter() {
		classIntercepted.twoParameter(validBeanA, validBeanB);
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodTwoParameter() {
		try {
			methodIntercepted.twoParameter(invalidBeanA, invalidBeanB);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 2 violations", message);
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodTwoParameter() {
		try {
			classIntercepted.twoParameter(invalidBeanA, invalidBeanB);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 2 violations", message);
		}
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodTwoParameterOneNull() {
		try {
			methodIntercepted.twoParameter(null, invalidBeanB);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodTwoParameterOneNull() {
		try {
			classIntercepted.twoParameter(invalidBeanA, null);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodTwoParameterOneSkipped() {
		try {
			methodIntercepted.twoParameterBSkipped(invalidBeanA, invalidBeanB);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodTwoParameterOneSkipped() {
		try {
			classIntercepted.twoParameterBSkipped(invalidBeanA, invalidBeanB);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodVarParameters() {
		try {
			methodIntercepted.varParameters(invalidBeanA, invalidBeanA, validBeanA, invalidBeanA);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 3 violations", message);
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodVarParameter() {
		try {
			classIntercepted.varParameters(invalidBeanA, validBeanA, invalidBeanA);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 2 violations", message);
		}
	}

	@Test
	public void testMethodInterceptedInvalidParameterMethodWithDefinedGroup() {
		try {
			methodIntercepted.withGroup(invalidBeanA);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}

	@Test
	public void testClassInterceptedInvalidParameterMethodWithDefinedGroup() {
		try {
			classIntercepted.withGroup(invalidBeanA);
		} catch (RuntimeException e) {
			String message = e.getMessage();

			assertTrue(e instanceof RuntimeException);
			assertEquals("Has 1 violations", message);
		}
	}
}
