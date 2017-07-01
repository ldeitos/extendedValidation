package com.github.ldeitos.constants;

import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validation.ValidationClosure;

/**
 * Project constants.
 *
 * @author <a href="mailto:leandro.deitos@gmail.com">Leandro Deitos</a>
 *
 */
public interface Constants {

	/**
	 * Default {@link ValidationClosure} qualifier.
	 */
	String DEFAULT_VALIDATION_CLOSURE_QUALIFIER = "extededValidation.default.closure";

	/**
	 * Default ExtendedValidation interceptor validation closure implementation.
	 */
	String DEFAULT_VALIDATION_CLOSURE = "com.github.ldeitos.validation.impl.util.DefaultValidationClosure";

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
	 * "(.*?)(:|=)(.*)".
	 */
	String INTERPOLATE_PARAMETER_PATTERN = "(.*?)(:|=)(.*)";

	/**
	 * Pattern to informed parameter in constraint message:
	 * "(\\{|\\[)(.*)(\\}|\\])".
	 */
	String PARAMETER_PATTERN = "(\\{|\\[)(.*)(\\}|\\])";

	String PRESENTATION_TEMPLATE_PATTERN = "\\$T";

	String PRESENTATION_MESSAGE_PATTERN = "\\$M";

	String PRESENTATION_MESSAGE_PARAMETER = "$M";

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
	 * SimplePath to template message presentation in xml configuration file.
	 */
	String PATH_CONF_TEMPLATE_MESSAGE_PRESENTATION = "template-message-presentation";

	/**
	 * SimplePath to {@link ValidationClosure} in xml configuration file.
	 */
	String PATH_CONF_VALIDATION_CLOSURE = "validation-closure";

	/**
	 * SimplePath to {@link MessagesSource} in xml configuration file.
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
