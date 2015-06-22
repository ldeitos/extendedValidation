package com.github.ldeitos.tarcius.exception;

public class ParameterValueResolveException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ParameterValueResolveException(String msg) {
		super(msg);
	}

	public ParameterValueResolveException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public static void throwNew(String msg) throws ParameterValueResolveException {
		throw new ParameterValueResolveException(msg);
	}
}
