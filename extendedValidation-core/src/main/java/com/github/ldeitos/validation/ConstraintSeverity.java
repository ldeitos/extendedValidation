package com.github.ldeitos.validation;

import jakarta.validation.Payload;

/**
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public class ConstraintSeverity implements Payload {
	public static final class ERROR extends ConstraintSeverity {
	};

	public static final class ALERT extends ConstraintSeverity {
	};

	public static final class INFO extends ConstraintSeverity {
	};

	public static final class WARN extends ConstraintSeverity {
	};

	public static final class FATAL extends ConstraintSeverity {
	};

	public static Class<? extends ConstraintSeverity> error() {
		return ERROR.class;
	}

	public static Class<? extends ConstraintSeverity> alert() {
		return ALERT.class;
	}

	public static Class<? extends ConstraintSeverity> info() {
		return INFO.class;
	}

	public static Class<? extends ConstraintSeverity> warn() {
		return WARN.class;
	}

	public static Class<? extends ConstraintSeverity> fatal() {
		return FATAL.class;
	}

	public static Class<? extends ConstraintSeverity> defaultValue() {
		return error();
	}
}
