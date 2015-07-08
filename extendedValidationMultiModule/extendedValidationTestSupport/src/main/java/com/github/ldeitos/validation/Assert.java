package com.github.ldeitos.validation;

import static java.lang.String.format;
import static java.util.Collections.sort;
import static org.apache.commons.collections4.CollectionUtils.collect;
import static org.apache.commons.collections4.CollectionUtils.containsAll;
import static org.apache.commons.collections4.CollectionUtils.size;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.collections4.Transformer;

public class Assert {

	private static final Transformer<Message, String> MESSAGE_TO_STRING = new Transformer<Message, String>() {

		@Override
		public String transform(Message input) {
			return input.getMessage();
		}
	};

	public static void assertContainMessage(String description, String expectedMessage,
		Collection<String> messages) {
		assertTrue(description, messages.contains(expectedMessage));
	}

	public static void assertContainMessage(String expectedMessage, Collection<String> messages) {
		String description = format("Unable to find expected message [%s] in message collection: {%s}",
			expectedMessage, messages);
		assertContainMessage(description, expectedMessage, messages);
	}

	public static <T> void assertContainMessageInContrainsts(String description, String expectedMessage,
		Collection<ConstraintViolation<T>> violations) {
		Transformer<ConstraintViolation<T>, String> constraintToString = new Transformer<ConstraintViolation<T>, String>() {

			@Override
			public String transform(ConstraintViolation<T> input) {
				return input.getMessage();
			}
		};

		assertContainMessage(description, expectedMessage, toList(violations, constraintToString));
	}

	public static <T> void assertContainMessageInConstraints(String expectedMessage,
		Collection<ConstraintViolation<T>> violations) {
		Transformer<ConstraintViolation<T>, String> constraintToString = new Transformer<ConstraintViolation<T>, String>() {

			@Override
			public String transform(ConstraintViolation<T> input) {
				return input.getMessage();
			}
		};
		assertContainMessage(expectedMessage, toList(violations, constraintToString));
	}

	public static <T> void assertContainMessageInContrainsts(String description, String expectedMessage,
	    ValidationRunner<T> runner) {
		assertContainMessageInContrainsts(description, expectedMessage, runner.doValidation());
	}

	public static <T> void assertContainMessageInConstraints(String expectedMessage,
	    ValidationRunner<T> runner) {
		assertContainMessageInConstraints(expectedMessage, runner.doValidation());
	}

	public static void assertContainMessageInMessages(String description, String expectedMessage,
		Collection<Message> messages) {
		assertContainMessage(description, expectedMessage, toList(messages, MESSAGE_TO_STRING));
	}

	public static void assertContainMessageInMessages(String description, String expectedMessage,
		ExtendedValidationRunner runner) {
		assertContainMessageInMessages(description, expectedMessage, runner.doValidation());
	}

	public static void assertContainMessageInMessages(String expectedMessage, Collection<Message> messages) {
		assertContainMessage(expectedMessage, toList(messages, MESSAGE_TO_STRING));
	}

	public static void assertContainMessageInMessages(String expectedMessage, ExtendedValidationRunner runner) {
		assertContainMessageInMessages(expectedMessage, runner.doValidation());
	}

	public static void assertContainsAllMessages(String description, Collection<String> expectedMessage,
	    Collection<String> messages) {
		boolean exprectedMaxSameSize = size(expectedMessage) <= size(messages);
		assertTrue(description, exprectedMaxSameSize && containsAll(messages, expectedMessage));
	}

	public static void assertContainsAllMessages(Collection<String> expectedMessage,
	    Collection<String> messages) {
		List<String> expected = new ArrayList<String>(expectedMessage);
		List<String> actual = new ArrayList<String>(messages);

		sort(expected);
		sort(actual);

		String description = format("Unable to find all expected messages {%s} in message collection: {%s}",
		    expected, actual);
		assertContainsAllMessages(description, expected, actual);
	}

	public static <T> void assertContainsAllMessagesInConstraints(Collection<String> expectedMessages,
		Collection<ConstraintViolation<T>> violations) {
		Transformer<ConstraintViolation<T>, String> constraintToString = new Transformer<ConstraintViolation<T>, String>() {

			@Override
			public String transform(ConstraintViolation<T> input) {
				return input.getMessage();
			}
		};
		assertContainsAllMessages(expectedMessages, toList(violations, constraintToString));
	}

	public static <T> void assertContainsAllMessagesInConstraints(String description,
		Collection<String> expectedMessages, Collection<ConstraintViolation<T>> violations) {
		Transformer<ConstraintViolation<T>, String> constraintToString = new Transformer<ConstraintViolation<T>, String>() {

			@Override
			public String transform(ConstraintViolation<T> input) {
				return input.getMessage();
			}
		};
		assertContainsAllMessages(description, expectedMessages, toList(violations, constraintToString));
	}

	public static <T> void assertContainsAllMessagesInConstraints(String description,
		Collection<String> expectedMessages, ValidationRunner<T> runner) {
		assertContainsAllMessagesInConstraints(description, expectedMessages, runner.doValidation());
	}

	public static <T> void assertContainsAllMessagesInConstraints(Collection<String> expectedMessages,
		ValidationRunner<T> runner) {
		assertContainsAllMessagesInConstraints(expectedMessages, runner.doValidation());
	}

	public static void assertContainsAllMessagesInMessages(String description,
		Collection<String> expectedMessage, Collection<Message> messages) {
		assertContainsAllMessages(description, expectedMessage, toList(messages, MESSAGE_TO_STRING));
	}

	public static void assertContainsAllMessagesInMessages(String description,
		Collection<String> expectedMessage, ExtendedValidationRunner runner) {
		assertContainsAllMessages(description, expectedMessage,
			toList(runner.doValidation(), MESSAGE_TO_STRING));
	}

	public static void assertContainsAllMessagesInMessages(Collection<String> expectedMessages,
		Collection<Message> messages) {
		assertContainsAllMessages(expectedMessages, toList(messages, MESSAGE_TO_STRING));
	}

	public static void assertContainsAllMessagesInMessages(Collection<String> expectedMessages,
		ExtendedValidationRunner runner) {
		assertContainsAllMessagesInMessages(expectedMessages, runner.doValidation());
	}

	public static <T> ValidationRunner<T> validate(final Validator v, final T toValidate,
		final Class<?>... groups) {
		return new ValidationRunner<T>() {
			@Override
			public Set<ConstraintViolation<T>> doValidation() {
				return v.validate(toValidate, groups);
			}
		};
	}

	public static ExtendedValidationRunner validate(final com.github.ldeitos.validation.Validator v,
	    final Object toValidate, final Class<?>... groups) {
		return new ExtendedValidationRunner() {
			@Override
			public Set<Message> doValidation() {
				return v.validateBean(toValidate, groups);
			}
		};
	}

	private static <T> List<String> toList(Collection<T> violations, Transformer<T, String> transformer) {
		List<String> list = new ArrayList<String>();

		collect(violations, transformer, list);
		sort(list);

		return list;
	}

	public static interface ValidationRunner<T> {
		Set<ConstraintViolation<T>> doValidation();
	}

	public static interface ExtendedValidationRunner {
		Set<Message> doValidation();
	}
}
