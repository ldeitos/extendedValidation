package com.github.ldeitos.tarcius.support;

public class AuditData {
	private StringBuilder message = new StringBuilder();

	public StringBuilder getMessage() {
		return message;
	}

	public String getStringMessage() {
		return message.toString();
	}

}
