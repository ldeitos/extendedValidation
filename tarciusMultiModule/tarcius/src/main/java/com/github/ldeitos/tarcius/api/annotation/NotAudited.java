package com.github.ldeitos.tarcius.api.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Identify a parameter annotated with {@link Audited} to be ignored in audit
 * process.
 * 
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface NotAudited {
}
