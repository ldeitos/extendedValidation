package com.github.ldeitos.validation.impl.util;

import static com.github.ldeitos.constants.Constants.MESSAGE_KEY_PATTERN;
import static com.github.ldeitos.constants.Constants.PARAMETER_CONTENT_GROUP;
import static com.github.ldeitos.validation.impl.configuration.Configuration.PRESENTATION_MESSAGE;
import static com.github.ldeitos.validation.impl.configuration.Configuration.PRESENTATION_TEMPLATE;
import static java.util.regex.Matcher.quoteReplacement;
import static java.util.regex.Pattern.compile;

import java.util.regex.Matcher;

import javax.enterprise.inject.spi.CDI;

import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.configuration.Configuration;

public class PresentationMessageFormatter {

	public static String format(String template, String message) {
		ConfigInfoProvider configProvider = CDI.current().select(ConfigInfoProvider.class).get();
		Configuration configuration = Configuration.getConfiguration(configProvider);
		Matcher matcherTemplate = compile(MESSAGE_KEY_PATTERN).matcher(template);
		String formatedMessage = message;

		if (configuration.showTemplate() && matcherTemplate.find()) {
			Matcher matcherMessage = PRESENTATION_MESSAGE.matcher(configuration
				.getMessagePresentationTemplate());
			formatedMessage = matcherMessage.replaceAll(quoteReplacement(message));
			Matcher matcherTemplateParam = PRESENTATION_TEMPLATE.matcher(formatedMessage);
			String msgKey = matcherTemplate.group(PARAMETER_CONTENT_GROUP);

			formatedMessage = matcherTemplateParam.replaceAll(quoteReplacement(msgKey));
			matcherTemplateParam = PRESENTATION_TEMPLATE.matcher(formatedMessage);
		}

		return formatedMessage;
	}
}