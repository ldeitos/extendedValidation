package com.github.ldeitos.tarcius.support;

public class MessageDestination {
	private static String message;

	public static String getMessage() {
		return message;
	}

	public static void setMessage(String message) {
		MessageDestination.message = message;
	}
}
