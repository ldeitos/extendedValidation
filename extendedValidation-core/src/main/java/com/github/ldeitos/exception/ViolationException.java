package com.github.ldeitos.exception;

import java.util.HashSet;
import java.util.Set;

import com.github.ldeitos.validation.Message;
import com.github.ldeitos.validation.Validator;

/**
 * Exception to throw when a object under validation has violations.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @see Validator#validateAndThrow(Object, Class...)
 *
 * @since 0.9.2
 */
public class ViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private Set<Message> messages = new HashSet<Message>();

	public ViolationException(String msg) {
		super(msg);
	}

	public ViolationException(String msg, Set<Message> messages) {
		this(msg);
		this.messages.addAll(messages);
	}

	public ViolationException(String msg, Throwable t) {
		super(msg, t);
	}

	public ViolationException(String msg, Throwable t, Set<Message> messages) {
		this(msg, t);
		this.messages.addAll(messages);
	}

	public static void throwNew(String msg) throws ViolationException {
		throw new ViolationException(msg);
	}

	public static void throwNew(String msg, Set<Message> messages) throws ViolationException {
		throw new ViolationException(msg, messages);
	}

	public static void throwNew(String msg, Throwable t) throws ViolationException {
		throw new ViolationException(msg, t);
	}

	public static void throwNew(String msg, Throwable t, Set<Message> messages) throws ViolationException {
		throw new ViolationException(msg, t, messages);
	}

	/**
	 * @return Violation messages.
	 */
	public Set<Message> getMessages() {
		return messages;
	}
}
