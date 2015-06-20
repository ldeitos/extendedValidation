package com.github.ldeitos.tarcius.audit.resolver;

import static com.github.ldeitos.tarcius.configuration.Constants.FORMATTED_STRING_RESOLVER_ID;
import static java.lang.String.format;

import javax.enterprise.context.ApplicationScoped;

import com.github.ldeitos.tarcius.api.ParameterResolver;
import com.github.ldeitos.tarcius.qualifier.CustomResolver;

/**
 * Default implementation to {@link ParameterResolver} to apply format by
 * {@link String#format(String, Object...)} method.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@ApplicationScoped
@CustomResolver(FORMATTED_STRING_RESOLVER_ID)
public class DefaultFormattedStringResolver extends FormattedStringResolver<Object> {

	@Override
	public String resolve(Object input) {
		return format(getFormat(), input);
	}
}
