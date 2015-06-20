package com.github.ldeitos.tarcius.audit.resolver;

import com.github.ldeitos.tarcius.api.ParameterFormattedResolver;
import com.github.ldeitos.tarcius.api.ParameterResolver;

/**
 * Abstract class to {@link ParameterResolver} that will apply format to
 * {@link String} output.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @param <T>
 */
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
