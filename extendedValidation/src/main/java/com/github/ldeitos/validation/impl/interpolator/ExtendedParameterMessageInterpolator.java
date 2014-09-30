package com.github.ldeitos.validation.impl.interpolator;

import static com.github.ldeitos.validation.impl.configuration.Configuration.getConfiguration;
import static javax.validation.Validation.byDefaultProvider;

import java.util.Locale;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.MessageInterpolator;

import com.github.ldeitos.validation.MessagesSource;

/**
 * 
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@ApplicationScoped
public class ExtendedParameterMessageInterpolator implements MessageInterpolator {
	
	MessageInterpolator delegate = byDefaultProvider().configure().getDefaultMessageInterpolator();

	MessagesSource messageSource;
	
	{
		messageSource = getConfiguration().getConfiguredMessagesSource();
	}

	public String interpolate(String messageTemplate, Context context) {
		String message = messageSource.getMessage(messageTemplate);
		
		return delegate.interpolate(message, new ExtendedParameterContext(context));
	}

	public String interpolate(String messageTemplate, Context context,
			Locale locale) {
		String message = messageSource.getMessage(messageTemplate, locale);
		
		return delegate.interpolate(message, new ExtendedParameterContext(context), locale);
	}
}
