package com.github.ldeitos.validation;

import java.io.Serializable;

import jakarta.validation.ConstraintViolation;

/**
 * Interface to validation generated messages.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public interface Message extends Serializable {
	/**
	 * @return The message {@link Severity}.
	 */
	Severity getSeverity();

	/**
	 * @return Violation generated message text.
	 */
	String getMessage();

	/**
	 * @return Original {@link ConstraintViolation}.
	 */
	ConstraintViolation<?> getOriginConstraint();
}
