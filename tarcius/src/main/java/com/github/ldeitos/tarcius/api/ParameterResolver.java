package com.github.ldeitos.tarcius.api;

/**
 * Interface to custom resolver to translate audited parameter.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @param <I>
 *            Generic type of input parameter to be audited.
 */
public interface ParameterResolver<I> {
	/**
	 * @param input
	 *            Parameter to be resolved.
	 * @return {@link String} representation of input value.
	 */
	String resolve(I input);
}
