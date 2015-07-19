package com.github.ldeitos.validation.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Identify a parameter to be skipped on validation process.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @since 0.9.2
 */
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface SkipValidation {
}
