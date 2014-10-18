package com.github.ldeitos.validation.impl.configuration.dto;

import static com.github.ldeitos.constants.Constants.DEFAULT_MESSAGE_SOURCE;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import static org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.github.ldeitos.exception.InvalidConfigurationException;

@XmlRootElement(name = "extended-validation")
@XmlAccessorType(FIELD)
public class ConfigurationDTO {
	@XmlElement(type = String.class, name = "message-source", defaultValue = DEFAULT_MESSAGE_SOURCE)
	private String messageSource;

	@XmlElement(name = "message-files")
	private List<MessageFileDTO> messageFiles = new ArrayList<MessageFileDTO>();

	public String getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(String messageSource) {
		this.messageSource = messageSource;
	}

	public List<MessageFileDTO> getMessageFiles() {
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
		Set<MessageFileDTO> currentContent = new HashSet<MessageFileDTO>(messageFiles);
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
	}
}
