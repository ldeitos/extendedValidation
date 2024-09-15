package com.github.ldeitos.exception;

public class InvalidConfigurationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidConfigurationException(String msg){
		super(msg);
	}
	
	public InvalidConfigurationException(String msg, Throwable t){
		super(msg, t);
	}

	public static void throwNew(String msg){
		throw new InvalidConfigurationException(msg);
	}
	
	public static void throwNew(String msg, Throwable t){
		throw new InvalidConfigurationException(msg, t);
	}
}
