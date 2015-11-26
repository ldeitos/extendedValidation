package com.github.ldeitos.restutil;

import static java.util.regex.Pattern.compile;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.commons.lang3.StringUtils.substring;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser to convert a String in {@link ParamDescriptor}.
 *
 * @author <a href="mailto:leandro.deitos@gmail.com">Leandro Deitos</a>
 *
 */
public class ParametersParser {

	private static final String COMPLEX_PARAM_BEGGIN = "(";

	private static final String COMPLEX_PARAM_END = ")";

	private static final Pattern SIMPLE_PARAM = compile("(\\w+)");

	private static final int GROUP_PARAM = 1;

	/**
	 * @param params
	 *            String representing parameter list. Must follow the pattern:<br>
	 * <br>
	 *            <ul>
	 *            <li>Each parameter must be a valid alphanumeric word.</li>
	 *            <li>Each parameter must be separated by comma.</li>
	 *            <li>Complex parameters must describe yours parameters under
	 *            parentheses.</li>
	 *            </ul>
	 *            Representation example:
	 *            <i>"kind,items(title,characteristics(length))"</i>
	 *
	 * @return Root {@link ParamDescriptor} with parameters list in your
	 *         {@link ParamDescriptor#getInnerParams()}
	 */
	public static ParamDescriptor parse(String params) {
		ParamDescriptor toReturn = new ParamDescriptor();
		Deque<List<ParamDescriptor>> paramsStack = new LinkedList<List<ParamDescriptor>>();
		paramsStack.add(toReturn.getInnerParams());
		String toParse = params == null ? null : new String(params);

		while (isNotBlank(toParse)) {
			Matcher matcher = SIMPLE_PARAM.matcher(toParse);

			if (!matcher.find()) {
				break;
			}

			String paramName = matcher.group(GROUP_PARAM);
			ParamDescriptor param = new ParamDescriptor(paramName);

			paramsStack.peekLast().add(param);
			toParse = matcher.replaceFirst(EMPTY).trim();

			if (startsWith(toParse, COMPLEX_PARAM_BEGGIN)) {
				paramsStack.add(param.getInnerParams());
			}

			while (startsWith(toParse, COMPLEX_PARAM_END)) {
				paramsStack.pollLast();
				toParse = substring(toParse, 1);
			}

			toParse = substring(toParse, 1);
		}

		return toReturn;
	}

}
