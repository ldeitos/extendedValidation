package com.github.ldeitos.validators;

import static java.lang.String.format;

import java.lang.annotation.Annotation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private Logger log = LoggerFactory.getLogger(AbstractExtendedValidator.class);

	private boolean isValid = true;

	private ConstraintValidatorContext context;

	@Inject
	private PreInterpolator preInterpolator;

	/**
	 * {@inheritDoc}<br>
	 * <b>P.S.:</b> Do not override, unless on your override you make
	 * {@link #doValidation(Object)} call;
	 */
	@Override
	public final boolean isValid(T value, ConstraintValidatorContext context) {
		this.context = context;

		doValidation(value);

		if (!isValid) {
			String logMsg = "[%s] value are invalided by [%s] validator call.";
			log.info(format(logMsg, value, this.getClass().getName()));
		}

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

		log.debug("Adding violation with default template.");
		doTraceLog(msgParameters);

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

		log.debug(format("Adding violation with [%s] template.", msgTemplate));
		doTraceLog(msgParameters);

		makeInvalid();

		context.buildConstraintViolationWithTemplate(msgInterpolated).addConstraintViolation();
	}

	private void doTraceLog(String[] msgParameters) {
		if (log.isTraceEnabled()) {
			for (int i = 0; i < msgParameters.length; i++) {
				String parameter = msgParameters[i];
				log.trace(format("Parameter #%d: %s", i, parameter));
			}
		}
	}

	private void makeInvalid() {
		if (isValid) {
			isValid = false;
			context.disableDefaultConstraintViolation();
			log.debug("Value marked as invalid.");
		}
	}
}
