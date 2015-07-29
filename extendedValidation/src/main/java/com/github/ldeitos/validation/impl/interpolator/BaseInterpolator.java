package com.github.ldeitos.validation.impl.interpolator;

import com.github.ldeitos.util.ManualContext;
import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.configuration.Configuration;

class BaseInterpolator {

	/**
	 * Messages source.
	 */
	private MessagesSource messageSource;

	public MessagesSource getMessageSource() {
		ConfigInfoProvider configProvider = ManualContext.lookupCDI(ConfigInfoProvider.class);

		Configuration configuration = Configuration.getConfiguration(configProvider);
		if (messageSource == null) {
			messageSource = configuration.getConfiguredMessagesSource();
		}

		return messageSource;
	}

}
