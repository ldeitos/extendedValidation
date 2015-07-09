package com.github.ldeitos.validation.impl.interpolator;

import static com.github.ldeitos.validation.impl.configuration.Configuration.getConfiguration;

import com.github.ldeitos.validation.MessagesSource;

class BaseInterpolator {

	/**
	 * Messages source.
	 */
	private MessagesSource messageSource;

	public MessagesSource getMessageSource() {
		if (messageSource == null) {
			messageSource = getConfiguration().getConfiguredMessagesSource();
		}

		return messageSource;
	}
}
