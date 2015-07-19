package com.github.ldeitos.validation.impl.interpolator;

import static com.github.ldeitos.validation.impl.configuration.Configuration.getConfiguration;

import com.github.ldeitos.util.ManualContext;
import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;

class BaseInterpolator {

	/**
	 * Messages source.
	 */
	private MessagesSource messageSource;

	public MessagesSource getMessageSource() {
		if (messageSource == null) {
			ConfigInfoProvider configProvider = ManualContext.lookupCDI(ConfigInfoProvider.class);
			messageSource = getConfiguration(configProvider).getConfiguredMessagesSource();
		}

		return messageSource;
	}
}
