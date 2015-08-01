package com.github.ldeitos.validation.impl;

import static com.github.ldeitos.validation.Severity.ALERT;
import static com.github.ldeitos.validation.Severity.ERROR;
import static com.github.ldeitos.validation.Severity.FATAL;
import static com.github.ldeitos.validation.Severity.INFO;
import static com.github.ldeitos.validation.Severity.WARN;
import static com.github.ldeitos.validation.impl.util.PresentationMessageFormatter.format;

import javax.inject.Inject;

import com.github.ldeitos.validation.Message;
import com.github.ldeitos.validation.MessageResolver;
import com.github.ldeitos.validation.Severity;
import com.github.ldeitos.validation.impl.interpolator.PreInterpolator;

/**
 * {@link MessageResolver} default implementation.
 * 
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public class MessageResolverImpl implements MessageResolver {

	@Inject
	private PreInterpolator interpolator;

	@Override
	public Message getMessage(Severity severity, String template, String... parameters) {
		String msg = format(template, interpolator.interpolate(template, parameters));
		return new MessageImpl(msg, severity);
	}

	@Override
	public Message getInfo(String template, String... parameters) {
		return getMessage(INFO, template, parameters);
	}

	@Override
	public Message getWarn(String template, String... parameters) {
		return getMessage(WARN, template, parameters);
	}

	@Override
	public Message getAlert(String template, String... parameters) {
		return getMessage(ALERT, template, parameters);
	}

	@Override
	public Message getError(String template, String... parameters) {
		return getMessage(ERROR, template, parameters);
	}

	@Override
	public Message getFatal(String template, String... parameters) {
		return getMessage(FATAL, template, parameters);
	}

}
