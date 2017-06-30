package com.github.ldeitos.validation.impl.util;

import static com.github.ldeitos.constants.Constants.INTERPOLATE_PARAMETER_PATTERN;
import static com.github.ldeitos.constants.Constants.PARAMETER_KEY_GROUP;
import static com.github.ldeitos.constants.Constants.PARAMETER_VALUE_GROUP;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.ldeitos.constants.Constants;

public class ParameterUtils {

	/**
	 * Patter compiled by {@link Constants#INTERPOLATE_PARAMETER_PATTERN}
	 */
	private static final Pattern PARAMS_PATTERN = Pattern.compile(INTERPOLATE_PARAMETER_PATTERN);

	/**
	 * @param parameters
	 * 		   Parameters array.
	 * 
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

			atributes.put(key, value);
		}

		return atributes;
	}
}
