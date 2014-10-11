package com.github.ldeitos.validation.impl.interpolator;

import static com.github.ldeitos.validation.impl.configuration.Configuration.getConfiguration;
import static javax.validation.Validation.byDefaultProvider;

import java.util.Locale;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.MessageInterpolator;

import com.github.ldeitos.validation.MessagesSource;

/**
 * ExtendedValidation default {@link MessageInterpolator} implementation.<br/>
 * Obtain the requested message from {@link MessagesSource} and send to default 
 * interpolator from concrete BeanValidation API implementation in use.
 * 
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 *@see MessagesSource
 *@see ExtendedParameterContext
 */
@ApplicationScoped
public class ExtendedParameterMessageInterpolator implements MessageInterpolator {
	
	/**
	 * Default interpolator from concrete BeanValidation API implementation in use.
	 */
	private MessageInterpolator delegate = byDefaultProvider().configure().getDefaultMessageInterpolator();

	/**
	 * Messages source.
	 */
	private MessagesSource messageSource;
	
	{
		messageSource = getConfiguration().getConfiguredMessagesSource();
	}

	/**
	 * Get message, using messageTemplate, in {@link MessagesSource} and
	 * delegate to default interpolator to parameters resolution.
	 */
	public String interpolate(String messageTemplate, Context context) {
		String message = messageSource.getMessage(messageTemplate);
		
		return delegate.interpolate(message, new ExtendedParameterContext(context));
	}

	/**
	 * Get message , using {@link MessageTemplate}, in {@link MessagesSource} and
	 * delegate to default interpolator to parameters resolution.
	 */
	public String interpolate(String messageTemplate, Context context,
			Locale locale) {
		String message = messageSource.getMessage(messageTemplate, locale);
		
		return delegate.interpolate(message, new ExtendedParameterContext(context), locale);
	}
}
