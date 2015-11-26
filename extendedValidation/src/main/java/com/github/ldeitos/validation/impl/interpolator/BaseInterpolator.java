package com.github.ldeitos.validation.impl.interpolator;

import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validation.impl.configuration.ConfigInfo;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.configuration.Configuration;

class BaseInterpolator {

	/**
	 * Messages source.
	 */
	private MessagesSource messageSource;

	public MessagesSource getMessageSource() {
		ConfigInfo configInfo = ConfigInfoProvider.getConfigInfo();

		Configuration configuration = Configuration.getConfiguration(configInfo);
		if (messageSource == null) {
			messageSource = configuration.getConfiguredMessagesSource();
		}

		return messageSource;
	}

}
