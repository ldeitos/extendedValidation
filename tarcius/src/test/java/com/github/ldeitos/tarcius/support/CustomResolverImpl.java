package com.github.ldeitos.tarcius.support;

import static java.lang.String.format;

import com.github.ldeitos.tarcius.api.ParameterResolver;
import com.github.ldeitos.tarcius.qualifier.CustomResolver;

@CustomResolver("customTeste")
public class CustomResolverImpl implements ParameterResolver<Teste> {

	@Override
	public String resolve(Teste input) {
		return format("CustomResolver: [%s]", input.getField());
	}

}
