package com.github.ldeitos.constants;

/**
 * Project constants.
 *
 * @author <a href="mailto:leandro.deitos@gmail.com">Leandro Deitos</a>
 *
 */
public interface Constants {
	/**
	 * Default ExtendedValidation interpolator implementation.
	 */
	String DEFAULT_MESSAGE_SOURCE = "com.github.ldeitos.validation.impl.interpolator.MultipleBundlesSource";

	/**
	 * ExtendedValidation configuration file name:
	 * "META-INF/extendedValidation.xml".
	 */
	String CONFIGURATION_FILE = "META-INF/extendedValidation.xml";

	/**
	 * System property to configure messages files collection.<br/>
	 * E.g: com.github.ldeitos.validation.message.files=arq1, arq2, arq3
	 */
	String MESSAGE_FILES_SYSTEM_PROPERTY = "com.github.ldeitos.validation.message.files";

	/**
	 * Default BeanValidation API message file name: "ValidationMessages".
	 */
	String DEFAULT_MESSAGE_FILE = "ValidationMessages";

	/**
	 * Messages parameters field name in constraint: "messageParameters".
	 */
	String PARAMETERS_FIELD_NAME = "messageParameters";

	/**
	 * Patter to message key: "^(\\{|\\[)(.*)(\\}|\\])$".
	 */
	String MESSAGE_KEY_PATTERN = "^(\\{|\\[)(.*)(\\}|\\])$";

	/**
	 * Pattern to informed parameter to be interpolated in constraint message:
	 * "(.*)(:|=)(.*)".
	 */
	String INTERPOLATE_PARAMETER_PATTERN = "(.*)(:|=)(.*)";

	/**
	 * Pattern to informed parameter in constraint message:
	 * "(\\{|\\[)(.*)(\\}|\\])".
	 */
	String PARAMETER_PATTERN = "(\\{|\\[)(.*)(\\}|\\])";

	/**
	 * Parameter group index in regex {@link Constants#PARAMETER_PATTERN}.
	 */
	int PARAMETER_GROUP = 0;

	/**
	 * Parameter content group index in regex
	 * {@link Constants#PARAMETER_PATTERN}.
	 */
	int PARAMETER_CONTENT_GROUP = 2;

	/**
	 * Parameter key group index in regex
	 * {@link Constants#INTERPOLATE_PARAMETER_PATTERN}.
	 */
	int PARAMETER_KEY_GROUP = 1;

	/**
	 * Parameter value group index in regex
	 * {@link Constants#INTERPOLATE_PARAMETER_PATTERN}.
	 */
	int PARAMETER_VALUE_GROUP = 3;

	/**
	 * SimplePath to MessagesSource in xml configuration file.
	 */
	String PATH_CONF_MESSAGE_SOURCE = "message-source";

	/**
	 * SimplePath to message file collection in xml configuration file.
	 */
	String PATH_CONF_MESSAGE_FILES = "message-files";

	/**
	 * SimplePath to each message file in collection.
	 */
	String PATH_CONF_MESSAGE_FILE = "message-file";

}
