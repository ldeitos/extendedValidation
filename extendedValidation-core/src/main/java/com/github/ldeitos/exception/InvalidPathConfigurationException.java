package com.github.ldeitos.exception;

public class InvalidPathConfigurationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidPathConfigurationException(String msg){
		super(msg);
	}
	
	public InvalidPathConfigurationException(String msg, Throwable t){
		super(msg, t);
	}

	public static void throwNew(String msg){
		throw new InvalidPathConfigurationException(msg);
	}
	
	public static void throwNew(String msg, Throwable t){
		throw new InvalidPathConfigurationException(msg, t);
	}
}
