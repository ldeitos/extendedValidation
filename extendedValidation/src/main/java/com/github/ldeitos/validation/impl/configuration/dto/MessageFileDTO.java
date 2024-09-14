package com.github.ldeitos.validation.impl.configuration.dto;

import com.github.ldeitos.constants.Constants;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * DTO to load configuration from {@link Constants#CONFIGURATION_FILE}
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @see Constants#PATH_CONF_MESSAGE_FILE
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageFileDTO {
	@XmlElement(name = "message-file")
	private String messageFile;
	
	public MessageFileDTO(){	
		this("");
	}

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
