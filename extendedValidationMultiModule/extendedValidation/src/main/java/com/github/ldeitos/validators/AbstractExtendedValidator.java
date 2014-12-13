package com.github.ldeitos.validators;

import java.lang.annotation.Annotation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validation.impl.interpolator.PreInterpolator;

/**
 * Abstraction to provide easy way to create validator with multiple violation
 * registers.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @param <A>
 *            Constraint annotation
 * @param <T>
 *            Type in validation.
 * 
 * @since 0.8.0
 */
public abstract class AbstractExtendedValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

	private boolean isValid = true;

	private ConstraintValidatorContext context;

	@Inject
	private PreInterpolator preInterpolator;

	@Override
	public final boolean isValid(T value, ConstraintValidatorContext context) {
		this.context = context;

		doValidation(value);

		return isValid;
	}

	/**
	 * Extension point to implement validation code. <br/>
	 * This code is invoked during API
	 * {@link #isValid(Object, ConstraintValidatorContext)} call and violation
	 * must be registered by any methods below:
	 * <ul>
	 * <li>{@link #addViolation(String, String...)}</li>
	 * <li>{@link #addViolationWithDefaultTemplate(String...)}</li>
	 * </ul>
	 *
	 * @param value
	 *            Value in validation.
	 *
	 * @since 0.8.0
	 */
	public abstract void doValidation(T value);

	/**
	 * Makes value on validation invalid and add a violation with default
	 * message template, defined in Constraint annotation, and parameters to
	 * interpolate.
	 *
	 * @param msgParameters
	 *            Parameters to be interpolated in message violation.<br>
	 *            Can be informed in "value" pattern, to be interpolated in
	 *            indexed parameter like "My {0} message" or in "key=value"
	 *            pattern, to be interpolated in defined parameter like
	 *            "My {par} message".
	 *
	 * @since 0.8.0
	 */
	protected void addViolationWithDefaultTemplate(String... msgParameters) {
		String msg = context.getDefaultConstraintMessageTemplate();
		String msgInterpolated = preInterpolator.interpolate(msg, msgParameters);

		makeInvalid();

		context.buildConstraintViolationWithTemplate(msgInterpolated).addConstraintViolation();
	}

	/**
	 * Makes value on validation invalid and add a violation with informed
	 * message template and parameters to interpolate.
	 *
	 * @param msgTemplate
	 *            Message template can be:<br>
	 *            - Just message text, like "My message";<br>
	 *            - Message text with parameters, like "My {0} message" or
	 *            "My {par} message";<br>
	 *            - Message key to get message in parameterized
	 *            {@link MessagesSource}, like {my.message.key}.
	 * @param msgParameters
	 *            Parameters to be interpolated in message violation.<br>
	 *            Can be informed in "value" pattern, to be interpolated in
	 *            indexed parameter like "My {0} message" or in "key=value"
	 *            pattern, to be interpolated in defined parameter like
	 *            "My {par} message".
	 *
	 * @since 0.8.0
	 */
	protected void addViolation(String msgTemplate, String... msgParameters) {
		String msgInterpolated = preInterpolator.interpolate(msgTemplate, msgParameters);

		makeInvalid();

		context.buildConstraintViolationWithTemplate(msgInterpolated).addConstraintViolation();
	}

	private void makeInvalid() {
		if (isValid) {
			isValid = false;
			context.disableDefaultConstraintViolation();
		}
	}
}