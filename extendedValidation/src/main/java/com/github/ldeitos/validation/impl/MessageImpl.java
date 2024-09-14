package com.github.ldeitos.validation.impl;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.lang.annotation.Annotation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Payload;
import jakarta.validation.metadata.ConstraintDescriptor;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.github.ldeitos.validation.ConstraintSeverity;
import com.github.ldeitos.validation.Message;
import com.github.ldeitos.validation.Severity;

public class MessageImpl implements Message {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Severity severity;

	private String message;

	private ConstraintViolation<?> originConstraint;

	MessageImpl(ConstraintViolation<?> originConstraint) {
		this(originConstraint.getMessage(), getSeverity(originConstraint.getConstraintDescriptor()));
		this.originConstraint = originConstraint;
	}

	MessageImpl(String message, Severity severity) {
		this.message = message;
		this.severity = severity;
	}

	@SuppressWarnings("unchecked")
	private static <T extends Annotation> Severity getSeverity(ConstraintDescriptor<T> descriptor) {
		Severity severity = Severity.ERROR;

		for (Class<? extends Payload> payload : descriptor.getPayload()) {
			if (ConstraintSeverity.class.isAssignableFrom(payload)) {
				severity = Severity.fromConstraintSeverity((Class<ConstraintSeverity>) payload);
				break;
			}
		}

		return severity;
	}

	@Override
	public Severity getSeverity() {
		return severity;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public ConstraintViolation<?> getOriginConstraint() {
		return originConstraint;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
	}

}
