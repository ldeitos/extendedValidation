package com.github.ldeitos.exception;

public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ValidationException(String msg){
		super(msg);
	}
	
	public ValidationException(String msg, Throwable t){
		super(msg, t);
	}

	public static void throwNew(String msg){
		throw new ValidationException(msg);
	}
	
	public static void throwNew(String msg, Throwable t){
		throw new ValidationException(msg, t);
	}
}
