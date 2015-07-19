package com.github.ldeitos.validation.impl.util;

import static com.github.ldeitos.constants.Constants.DEFAULT_VALIDATION_CLOSURE_QUALIFIER;

import java.util.Set;

import com.github.ldeitos.exception.ViolationException;
import com.github.ldeitos.qualifier.Closure;
import com.github.ldeitos.validation.Message;
import com.github.ldeitos.validation.ValidationClosure;

/**
 * Detault {@link ValidationClosure} implementation. <br>
 * Just throw a {@link ViolationException} containing received messages
 * violations.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@Closure(DEFAULT_VALIDATION_CLOSURE_QUALIFIER)
public class DefaultValidationClosure implements ValidationClosure {

	@Override
	public void proceed(Set<Message> messages) throws Exception {
		ViolationException.throwNew("Validation process generated violations.", messages);
	}
}
