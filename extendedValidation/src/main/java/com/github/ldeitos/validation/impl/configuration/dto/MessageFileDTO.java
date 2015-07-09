package com.github.ldeitos.validation.impl.configuration.dto;

import com.github.ldeitos.constants.Constants;

/**
 * DTO to load configuration from {@link Constants#CONFIGURATION_FILE}
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @see Constants#PATH_CONF_MESSAGE_FILE
 *
 */
public class MessageFileDTO {
	private String messageFile;

	public MessageFileDTO(String fileName) {
		messageFile = fileName;
	}

	public String getMessageFile() {
		return messageFile;
	}

	public void setMessageFile(String messageFie) {
		messageFile = messageFie;
	}

	@Override
	public String toString() {
		return messageFile;
	}
}
