package com.github.ldeitos.tarcius.audit.resolver;

import javax.enterprise.context.ApplicationScoped;

import com.github.ldeitos.tarcius.api.ParameterFormattedResolver;
import com.github.ldeitos.tarcius.api.ParameterResolver;

@ApplicationScoped
public abstract class FormattedStringResolver<T> implements ParameterFormattedResolver<T> {
	private String format;

	protected String getFormat() {
		return format;
	}

	@Override
	public ParameterResolver<T> applyFormat(String format) {
		this.format = format;
		return this;
	}

}
