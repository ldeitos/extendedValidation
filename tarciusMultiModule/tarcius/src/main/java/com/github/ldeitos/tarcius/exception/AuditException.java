package com.github.ldeitos.tarcius.exception;

public class AuditException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AuditException(String msg) {
		super(msg);
	}

	public AuditException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public static void throwNew(String msg) throws AuditException {
		throw new AuditException(msg);
	}

	public static void throwNew(String msg, Throwable cause) throws AuditException {
		throw new AuditException(msg, cause);
	}

}
