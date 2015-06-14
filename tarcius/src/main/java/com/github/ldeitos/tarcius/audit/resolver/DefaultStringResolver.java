package com.github.ldeitos.tarcius.audit.resolver;

import static com.github.ldeitos.tarcius.config.ConfigConstants.STRING_RESOLVER_ID;

import javax.enterprise.context.ApplicationScoped;

import com.github.ldeitos.tarcius.api.ParameterResolver;
import com.github.ldeitos.tarcius.qualifier.CustomResolver;

@ApplicationScoped
@CustomResolver(STRING_RESOLVER_ID)
public class DefaultStringResolver implements ParameterResolver<Object> {

	@Override
	public String resolve(Object input) {
		return String.valueOf(input);
	}

}
