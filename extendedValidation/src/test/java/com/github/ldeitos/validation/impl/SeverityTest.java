package com.github.ldeitos.validation.impl;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import io.github.cdiunit.AdditionalClasses;
import org.junit.Test;

import com.github.ldeitos.constraint.AssertTrue;
import com.github.ldeitos.test.base.BaseTest;
import com.github.ldeitos.validation.ConstraintSeverity;
import com.github.ldeitos.validation.Message;
import com.github.ldeitos.validation.Severity;
import com.github.ldeitos.validation.impl.interpolator.TestMessageSource;

@AdditionalClasses({ TestMessageSource.class })
public class SeverityTest extends BaseTest {

	@Test
	public void testErrorDeclared() {
		ErrorDeclarredTest bean = new ErrorDeclarredTest();

		Set<Message> messages = getExtendedValidator().validateBean(bean);
		Message message = messages.iterator().next();

		assertEquals(Severity.ERROR, message.getSeverity());
	}

	@Test
	public void testErrorDecefault() {
		ErrorDefaultTest bean = new ErrorDefaultTest();

		Set<Message> messages = getExtendedValidator().validateBean(bean);
		Message message = messages.iterator().next();

		assertEquals(Severity.ERROR, message.getSeverity());
	}

	@Test
	public void testInfo() {
		InfoTest bean = new InfoTest();

		Set<Message> messages = getExtendedValidator().validateBean(bean);
		Message message = messages.iterator().next();

		assertEquals(Severity.INFO, message.getSeverity());
	}

	@Test
	public void testWarn() {
		WarnTest bean = new WarnTest();

		Set<Message> messages = getExtendedValidator().validateBean(bean);
		Message message = messages.iterator().next();

		assertEquals(Severity.WARN, message.getSeverity());
	}

	@Test
	public void testAlert() {
		AlertTest bean = new AlertTest();

		Set<Message> messages = getExtendedValidator().validateBean(bean);
		Message message = messages.iterator().next();

		assertEquals(Severity.ALERT, message.getSeverity());
	}

	@Test
	public void testFatal() {
		FatalTest bean = new FatalTest();

		Set<Message> messages = getExtendedValidator().validateBean(bean);
		Message message = messages.iterator().next();

		assertEquals(Severity.FATAL, message.getSeverity());
	}

	private class ErrorDefaultTest {
		@AssertTrue(message = "Error")
		boolean value;
	}

	private class ErrorDeclarredTest {
		@AssertTrue(message = "Error", payload = { ConstraintSeverity.ERROR.class })
		boolean value;
	}

	private class InfoTest {
		@AssertTrue(message = "Error", payload = { ConstraintSeverity.INFO.class })
		boolean value;
	}

	private class AlertTest {
		@AssertTrue(message = "Error", payload = { ConstraintSeverity.ALERT.class })
		boolean value;
	}

	private class WarnTest {
		@AssertTrue(message = "Error", payload = { ConstraintSeverity.WARN.class })
		boolean value;
	}

	private class FatalTest {
		@AssertTrue(message = "Error", payload = { ConstraintSeverity.FATAL.class })
		boolean value;
	}
}
