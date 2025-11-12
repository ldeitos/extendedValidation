package com.github.ldeitos.validation.impl.interpolator;

import static com.github.ldeitos.constants.Constants.PARAMETER_CONTENT_GROUP;
import static com.github.ldeitos.constants.Constants.PARAMETER_PATTERN;
import static com.github.ldeitos.validation.impl.util.ParameterUtils.buildParametersMap;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.regex.Matcher.quoteReplacement;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validators.AbstractExtendedValidator;

import jakarta.enterprise.context.Dependent;

import java.util.Locale;

/**
 * Pre-interpolator called by {@link AbstractExtendedValidator} to make
 * interpolation from parameters informated during violation register.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @since 0.8.0
 */
@Dependent
public class PreInterpolator extends BaseInterpolator {

	private static final Pattern PARAM_PATTERN = Pattern.compile(PARAMETER_PATTERN);

	private Logger log = LoggerFactory.getLogger(PreInterpolator.class);

	/**
	 * @param msg
	 *            Message text template or key to retrieve message template in
	 *            configured {@link MessagesSource}.
	 * @param parameters
	 *            Parameters to be interpolated in message template. Can be
	 *            informed in "value" pattern, to be interpolated in indexed
	 *            parameter like "My {0} message" or in "key=value" pattern, to
	 *            be interpolated in defined parameter like "My {par} message".
	 * @return Resolved message by message template and parameters informed.
	 *
	 * @since 0.8.0
	 */
	public String interpolate(String msg, String... parameters) {
		log.debug(format("Message template: [%s]", msg));
		String resolvedMsg = getMessageSource().getMessage(msg);
		resolvedMsg = doInterpolation(resolvedMsg, parameters);
		return resolvedMsg;
	}

	/**
	 * @param msg
	 *            Message text template or key to retrieve message template in
	 *            configured {@link MessagesSource}.
	 *
	 * @param locale object represents a specific geographical, political, or cultural region.
	 *            
	 * @param parameters
	 *            Parameters to be interpolated in message template. Can be
	 *            informed in "value" pattern, to be interpolated in indexed
	 *            parameter like "My {0} message" or in "key=value" pattern, to
	 *            be interpolated in defined parameter like "My {par} message".
	 * @return Resolved message by message template and parameters informed.
	 *
	 */
	public String interpolate(String msg, Locale locale, String... parameters) {
		log.debug(format("Message template: [%s]", msg));
		String resolvedMsg = getMessageSource().getMessage(msg, locale);
		resolvedMsg = doInterpolation(resolvedMsg, parameters);
		return resolvedMsg;
	}
	
	
	private String doInterpolation(String msg, String... parameters) {
		String key;
		Object value;
		String toInterpolate = new String(msg);
		Map<String, Object> paramsMap = addParamMarkOnMapKeys(buildParametersMap(parameters));
		Matcher paramPatternMatcher = PARAM_PATTERN.matcher(msg);
		Matcher paramMatcher;

		log.debug(format("Message to interpolate: [%s]", toInterpolate));

		if (paramPatternMatcher.find()) {
			for (Entry<String, Object> entry : paramsMap.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				paramMatcher = Pattern.compile(key).matcher(toInterpolate);

				if (paramMatcher.find()) {
					logTrace(key, value);
					toInterpolate = paramMatcher.replaceAll(quoteReplacement(valueOf(value)));
				}
			}
		}

		log.debug(format("Message after interpolation: [%s]", toInterpolate));

		return toInterpolate;
	}

	private Map<String, Object> addParamMarkOnMapKeys(Map<String, Object> map) {
		Map<String, Object> retorno = new HashMap<String, Object>();
		Matcher paramPatternMatcher;

		for (Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			paramPatternMatcher = PARAM_PATTERN.matcher(key);

			log.trace(format("Parameter: key=[%s], value=[%s]", key, value));

			if (!paramPatternMatcher.matches()) {
				key = "\\{" + key + "\\}";
			}

			retorno.put(key, value);
		}

		return retorno;
	}

	private void logTrace(String key, Object value) {
		if (log.isTraceEnabled()) {
			Matcher matcher = PARAM_PATTERN.matcher(key);

			if (matcher.matches()) {
				log.trace(format("Parameter [%s] resolved to [%s]", matcher.group(PARAMETER_CONTENT_GROUP),
					valueOf(value)));
			}
		}
	}
}
