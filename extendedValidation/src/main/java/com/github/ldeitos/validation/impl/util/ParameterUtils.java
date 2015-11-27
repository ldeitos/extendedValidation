package com.github.ldeitos.validation.impl.util;

import static com.github.ldeitos.constants.Constants.INTERPOLATE_PARAMETER_PATTERN;
import static com.github.ldeitos.constants.Constants.PARAMETER_KEY_GROUP;
import static com.github.ldeitos.constants.Constants.PARAMETER_PATTERN;
import static com.github.ldeitos.constants.Constants.PARAMETER_VALUE_GROUP;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.ldeitos.constants.Constants;
import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validation.impl.configuration.ConfigInfo;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;
import com.github.ldeitos.validation.impl.configuration.Configuration;

public class ParameterUtils {

	/**
	 * Patter compiled by {@link Constants#INTERPOLATE_PARAMETER_PATTERN}
	 */
	private static final Pattern PARAM_IN_RESOURCE_PATTERN = Pattern.compile(PARAMETER_PATTERN);

	/**
	 * Patter compiled by {@link Constants#INTERPOLATE_PARAMETER_PATTERN}
	 */
	private static final Pattern PARAMS_PATTERN = Pattern.compile(INTERPOLATE_PARAMETER_PATTERN);

	private static MessagesSource messagesSource;

	/**
	 * @return Map of constraint attributes and respective values aggregated by
	 *         {@link Constants#PARAMETERS_FIELD_NAME} constraint content.
	 */
	public static Map<String, Object> buildParametersMap(String... parameters) {
		Map<String, Object> atributes = new HashMap<String, Object>();

		for (int i = 0; i < parameters.length; i++) {
			String par = parameters[i].trim();
			Matcher matcher = PARAMS_PATTERN.matcher(par);
			String key, value;

			if (matcher.matches()) {
				key = matcher.group(PARAMETER_KEY_GROUP);
				value = matcher.group(PARAMETER_VALUE_GROUP);
			} else {
				key = String.valueOf(i);
				value = par;
			}

			atributes.put(key, resolveParam(value));
		}

		return atributes;
	}

	/**
	 *
	 * @param par
	 * @return
	 * @since 1.0.CR3
	 */
	public static String resolveParam(String par) {
		String toReturn = par;

		if (toReturn != null && toReturn instanceof String) {
			Matcher matcher = PARAM_IN_RESOURCE_PATTERN.matcher(toReturn);

			if (matcher.matches()) {
				toReturn = getMessagesSource().getMessage(toReturn);
			}
		}

		return toReturn;
	}

	private static MessagesSource getMessagesSource() {
		if (messagesSource == null) {
			ConfigInfo configInfo = ConfigInfoProvider.getConfigInfo();
			Configuration configuration = Configuration.getConfiguration(configInfo);
			messagesSource = configuration.getConfiguredMessagesSource();
		}

		return messagesSource;
	}
}
