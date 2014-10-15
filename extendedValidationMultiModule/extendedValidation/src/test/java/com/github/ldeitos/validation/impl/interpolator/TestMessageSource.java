package com.github.ldeitos.validation.impl.interpolator;

import java.util.Locale;

import com.github.ldeitos.validation.MessagesSource;

public class TestMessageSource implements MessagesSource {

	public String getMessage(String key) {
		return key;
	}

	public String getMessage(String key, Locale locale) {
		return key;
	}

}
