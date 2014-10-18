package com.github.ldeitos.exception;

/**
 * Exception thrower when CDI Context aren't started.
 * 
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public class InvalidCDIContextException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidCDIContextException(String msg) {
		super(msg);
	}
	
	public InvalidCDIContextException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public static void throwNew(String msg, Throwable cause) {
		throw new InvalidCDIContextException(msg, cause);
	}
	
	public static void throwNew(String msg) {
		throw new InvalidCDIContextException(msg);
	}
	
	

}
