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
	 * ExtendedValidation configuration file name: "extendedValidation.xml".
	 */
	String CONFIGURATION_FILE = "extendedValidation.xml";
	
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
	String KEY_PATTERN = "^(\\{|\\[)(.*)(\\}|\\])$";
	
	/**
	 * Pattern to informed parameter to be interpolated in constraint message: "(.*)(:|=)(.*)".
	 */
	String PARAMETER_PATTERN = "(.*)(:|=)(.*)";
	
}
