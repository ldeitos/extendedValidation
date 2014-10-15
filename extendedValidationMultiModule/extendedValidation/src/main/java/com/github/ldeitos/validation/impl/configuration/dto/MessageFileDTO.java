package com.github.ldeitos.validation.impl.configuration.dto;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="message-files")
@XmlAccessorType(FIELD)
public class MessageFileDTO {
	@XmlElement(type= String.class, name="message-file", defaultValue = "")
	private String messageFile;
	
	public String getMessageFile() {
		return messageFile;
	}
	
	public void setMessageFile(String messageFie) {
		this.messageFile = messageFie;
	}
	
	@Override
	public String toString() {
		return messageFile;
	}
}
