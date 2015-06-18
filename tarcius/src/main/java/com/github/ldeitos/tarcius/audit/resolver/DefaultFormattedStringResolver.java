package com.github.ldeitos.tarcius.audit.resolver;

import static com.github.ldeitos.tarcius.configuration.Constants.FORMATTED_STRING_RESOLVER_ID;
import static java.lang.String.format;

import com.github.ldeitos.tarcius.qualifier.CustomResolver;

@CustomResolver(FORMATTED_STRING_RESOLVER_ID)
public class DefaultFormattedStringResolver extends FormattedStringResolver<Object> {

	@Override
	public String resolve(Object input) {
		return format(getFormat(), input);
	}
}
