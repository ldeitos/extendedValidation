package extended.validation.impl.interpolator;

import java.util.Locale;

import extended.validation.MessagesSource;

public class TestMessageSource implements MessagesSource {

	public String getMessage(String key) {
		return key;
	}

	public String getMessage(String key, Locale locale) {
		return key;
	}

}
