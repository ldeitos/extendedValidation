package com.github.ldeitos.validation;

/**
 * Resolves messages from {@link MessagesSource}, interpolate message parameters
 * and return a {@link Message} instance.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @since 0.9.3
 */
public interface MessageResolver {
	/**
	 * @param severity
	 *            Severity to be attributed to message.
	 * @param template
	 *            Template to message. <br>
	 *            When informed in "{key}" pattern, try retrieve message from
	 *            {@link MessagesSource} or uses template values if not.
	 * @param parameters
	 *            Parameter to be resolved in message text. <br>
	 *            Can be informed in "value" pattern, to be interpolated in
	 *            indexed parameter like "My {0} message" or in "key=value"
	 *            pattern, to be interpolated in defined parameter like
	 *            "My {par} message".
	 * @return {@link Message} instance with informed severity.
	 */
	Message getMessage(Severity severity, String template, String... parameters);

	/**
	 * @param template
	 *            Template to message. <br>
	 *            When informed in "{key}" pattern, try retrieve message from
	 *            {@link MessagesSource} or uses template values if not.
	 * @param parameters
	 *            Parameter to be resolved in message text. <br>
	 *            Can be informed in "value" pattern, to be interpolated in
	 *            indexed parameter like "My {0} message" or in "key=value"
	 *            pattern, to be interpolated in defined parameter like
	 *            "My {par} message".
	 * @return {@link Message} instance with {@link Severity#INFO} severity.
	 */
	Message getInfo(String template, String... parameters);

	/**
	 * @param template
	 *            Template to message. <br>
	 *            When informed in "{key}" pattern, try retrieve message from
	 *            {@link MessagesSource} or uses template values if not.
	 * @param parameters
	 *            Parameter to be resolved in message text. <br>
	 *            Can be informed in "value" pattern, to be interpolated in
	 *            indexed parameter like "My {0} message" or in "key=value"
	 *            pattern, to be interpolated in defined parameter like
	 *            "My {par} message".
	 * @return {@link Message} instance with {@link Severity#WARN} severity.
	 */
	Message getWarn(String template, String... parameters);

	/**
	 * @param template
	 *            Template to message. <br>
	 *            When informed in "{key}" pattern, try retrieve message from
	 *            {@link MessagesSource} or uses template values if not.
	 * @param parameters
	 *            Parameter to be resolved in message text. <br>
	 *            Can be informed in "value" pattern, to be interpolated in
	 *            indexed parameter like "My {0} message" or in "key=value"
	 *            pattern, to be interpolated in defined parameter like
	 *            "My {par} message".
	 * @return {@link Message} instance with {@link Severity#ALERT} severity.
	 */
	Message getAlert(String template, String... parameters);

	/**
	 * @param template
	 *            Template to message. <br>
	 *            When informed in "{key}" pattern, try retrieve message from
	 *            {@link MessagesSource} or uses template values if not.
	 * @param parameters
	 *            Parameter to be resolved in message text. <br>
	 *            Can be informed in "value" pattern, to be interpolated in
	 *            indexed parameter like "My {0} message" or in "key=value"
	 *            pattern, to be interpolated in defined parameter like
	 *            "My {par} message".
	 * @return {@link Message} instance with {@link Severity#ERROR} severity.
	 */
	Message getError(String template, String... parameters);

	/**
	 * @param template
	 *            Template to message. <br>
	 *            When informed in "{key}" pattern, try retrieve message from
	 *            {@link MessagesSource} or uses template values if not.
	 * @param parameters
	 *            Parameter to be resolved in message text. <br>
	 *            Can be informed in "value" pattern, to be interpolated in
	 *            indexed parameter like "My {0} message" or in "key=value"
	 *            pattern, to be interpolated in defined parameter like
	 *            "My {par} message".
	 * @return {@link Message} instance with {@link Severity#FATAL} severity.
	 */
	Message getFatal(String template, String... parameters);
}
