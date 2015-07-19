package com.github.ldeitos.test.base.stubs;

import static java.lang.String.format;

import java.util.Set;

import com.github.ldeitos.qualifier.Closure;
import com.github.ldeitos.validation.Message;
import com.github.ldeitos.validation.ValidationClosure;

@Closure("OtherTest")
public class TestOtherCustomValidationClosure implements ValidationClosure {

	@Override
	public void proceed(Set<Message> messages) throws Exception {
		throw new RuntimeException(format("Has %d violations [OTHER]", messages.size()));

	}
}
