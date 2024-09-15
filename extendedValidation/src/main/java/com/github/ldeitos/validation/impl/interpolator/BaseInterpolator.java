package com.github.ldeitos.validation.impl.interpolator;

import jakarta.enterprise.inject.spi.CDI;

import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.configuration.Configuration;

class BaseInterpolator {

	/**
	 * Messages source.
	 */
	private MessagesSource messageSource;

	public MessagesSource getMessageSource() {
		ConfigInfoProvider configProvider = CDI.current().select(ConfigInfoProvider.class).get();

		Configuration configuration = Configuration.getConfiguration(configProvider);
		if (messageSource == null) {
			messageSource = configuration.getConfiguredMessagesSource();
		}

		return messageSource;
	}

}
