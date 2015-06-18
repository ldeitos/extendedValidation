package com.github.ldeitos.tarcius.exception;

public class InvalidConfigurationException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidConfigurationException(String msg) {
		super(msg);
	}

	public InvalidConfigurationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public static void throwNew(String msg) throws InvalidConfigurationException {
		throw new InvalidConfigurationException(msg);
	}

	public static void throwNew(String msg, Throwable cause) throws InvalidConfigurationException {
		throw new InvalidConfigurationException(msg, cause);
	}

}
