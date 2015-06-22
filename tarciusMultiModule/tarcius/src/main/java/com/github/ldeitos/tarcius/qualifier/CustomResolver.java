package com.github.ldeitos.tarcius.qualifier;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import com.github.ldeitos.tarcius.api.ParameterResolver;

/**
 * Qualifier to be applied at custom {@link ParameterResolver} implementation.
 * Used by audit process to get correct {@link ParameterResolver} instance to
 * resolve, or translate, parameter value.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@Qualifier
@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD, PARAMETER })
public @interface CustomResolver {
	String value();
}
