package com.github.ldeitos.tarcius.api;

/**
 * Interface to custom resolver to translate audited parameter.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @param <I>
 *            Generic type of input parameter to be audited.
 */
public interface ParameterFormattedResolver<I> extends ParameterResolver<I> {
	/**
	 * @param format
	 *            Format string to be applied when input is resolved.
	 * @param input
	 *            Value to be resolved.
	 * @return Current resolver instance, enabling {@link #resolve(Object)}
	 *         nested call.
	 */
	String resolve(String format, I input);
}
