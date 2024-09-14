package com.github.ldeitos.validation.impl.configuration.dto;

import static com.github.ldeitos.constants.Constants.DEFAULT_MESSAGE_SOURCE;
import static com.github.ldeitos.constants.Constants.DEFAULT_VALIDATION_CLOSURE;
import static com.github.ldeitos.constants.Constants.PRESENTATION_MESSAGE_PARAMETER;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.github.ldeitos.constants.Constants;
import com.github.ldeitos.exception.InvalidConfigurationException;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * DTO to load configuration from {@link Constants#CONFIGURATION_FILE}
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "extended-validation")
public class ConfigurationDTO {
	
	@XmlElement(name = "message-source")
	private String messageSource = DEFAULT_MESSAGE_SOURCE;
	
	@XmlElement(name = "validation-closure")
	private String validationClosure = DEFAULT_VALIDATION_CLOSURE;

	@XmlElement(name = "message-presentation-template")
	private String messagePesentationTemplate = PRESENTATION_MESSAGE_PARAMETER;

	@XmlElementWrapper(name = "message-files")
	@XmlElement(name = "message-file")
	private List<String> messageFiles = new ArrayList<String>();

	public String getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(String messageSource) {
		this.messageSource = messageSource;
	}

	public String getValidationClosure() {
		return validationClosure;
	}

	
	public void setMessagePresentationTemplate(String templateMessagePresentation) {
		messagePesentationTemplate = templateMessagePresentation;
	}

	public String getMessagePresentationTemplate() {
		return messagePesentationTemplate;
	}
	
	public void setValidationClosure(String validationClosure) {
		this.validationClosure = validationClosure;
	}

	public List<String> getMessageFiles() {
		return messageFiles;
	}

	public void merge(ConfigurationDTO toMerge) {
		if (toMerge == this) {
			return;
		}

		assertUniqueMessageSourceName(toMerge);

		mergeMessageSource(toMerge);
		mergeMessageFiles(toMerge);
	}

	private void mergeMessageSource(ConfigurationDTO toMerge) {
		if (getMessageSource() == null) {
			messageSource = toMerge.getMessageSource();
		}
	}

	private void mergeMessageFiles(ConfigurationDTO toMerge) {
		Set<String> currentContent = new HashSet<String>(messageFiles);
		currentContent.addAll(toMerge.getMessageFiles());
		messageFiles.clear();
		messageFiles.addAll(currentContent);
	}

	private void assertUniqueMessageSourceName(ConfigurationDTO toMerge) {
		boolean invalido = getMessageSource() != null && toMerge.getMessageFiles() != null;
		invalido &= !StringUtils.equals(messageSource, toMerge.getMessageSource());

		if (invalido) {
			InvalidConfigurationException.throwNew("Multiple configuration files in application class "
				+ "path containing different MessagesSource references.");
		}
	}

	public void addMessageFile(String fileName) {
		getMessageFiles().add(fileName);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
	}
}
