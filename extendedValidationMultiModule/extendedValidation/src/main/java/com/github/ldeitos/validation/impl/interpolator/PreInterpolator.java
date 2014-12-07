package com.github.ldeitos.validation.impl.interpolator;

import static com.github.ldeitos.constants.Constants.PARAMETER_PATTERN;
import static com.github.ldeitos.validation.impl.util.ParameterUtils.buildParametersMap;
import static java.lang.String.valueOf;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreInterpolator extends BaseInterpolator {

	private static final Pattern PARAM_PATTERN = Pattern.compile(PARAMETER_PATTERN);

	public String interpolate(String msg, String... parameters) {
		String resolvedMsg = getMessageSource().getMessage(msg);

		return doInterpolation(resolvedMsg, parameters);
	}

	private String doInterpolation(String msg, String... parameters) {
		String toInterpolate = new String(msg);
		Map<String, Object> paramsMap = addParamMarkOnMapKeys(buildParametersMap(parameters));
		Matcher paramPatternMatcher = PARAM_PATTERN.matcher(msg);
		Matcher paramMatcher;

		if (paramPatternMatcher.find()) {
			for (Entry<String, Object> entry : paramsMap.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				paramMatcher = Pattern.compile(key).matcher(toInterpolate);

				if (paramMatcher.find()) {
					toInterpolate = paramMatcher.replaceAll(valueOf(value));
				}
			}
		}

		return toInterpolate;
	}

	private Map<String, Object> addParamMarkOnMapKeys(Map<String, Object> map) {
		Map<String, Object> retorno = new HashMap<String, Object>();
		Matcher paramPatternMatcher;

		for (Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			paramPatternMatcher = PARAM_PATTERN.matcher(key);

			if (paramPatternMatcher.matches()) {
				continue;
			}

			retorno.put("\\{" + key + "\\}", value);
		}

		return retorno;
	}
}
