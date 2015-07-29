package com.github.ldeitos.validation.impl.interpolator;

import static com.github.ldeitos.constants.Constants.MESSAGE_KEY_PATTERN;
import static com.github.ldeitos.constants.Constants.PARAMETER_CONTENT_GROUP;
import static com.github.ldeitos.validation.impl.configuration.Configuration.PRESENTATION_MESSAGE;
import static com.github.ldeitos.validation.impl.configuration.Configuration.PRESENTATION_TEMPLATE;
import static java.util.regex.Pattern.compile;

import java.util.regex.Matcher;

import com.github.ldeitos.util.ManualContext;
import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.configuration.Configuration;

class BaseInterpolator {

	/**
	 * Messages source.
	 */
	private MessagesSource messageSource;

	private Configuration configuration;

	public MessagesSource getMessageSource() {
		if (messageSource == null) {
			messageSource = getConfiguration().getConfiguredMessagesSource();
		}

		return messageSource;
	}

	private Configuration getConfiguration() {
		if (configuration == null) {
			ConfigInfoProvider configProvider = ManualContext.lookupCDI(ConfigInfoProvider.class);

			configuration = Configuration.getConfiguration(configProvider);
		}
		return configuration;
	}

	protected String formatMessagePresentation(String template, String message) {
		Matcher matcherTemplate = compile(MESSAGE_KEY_PATTERN).matcher(template);
		Matcher matcherMessage = PRESENTATION_MESSAGE.matcher(getConfiguration()
			.getMessagePresentationTemplate());
		String formatedMessage = matcherMessage.replaceAll(message);

		if (configuration.showTemplate() && matcherTemplate.find()) {
			String msgKey = matcherTemplate.group(PARAMETER_CONTENT_GROUP);
			Matcher matcherTemp = PRESENTATION_TEMPLATE.matcher(formatedMessage);
			formatedMessage = matcherTemp.replaceAll(msgKey);
		}

		return formatedMessage;
	}
}
