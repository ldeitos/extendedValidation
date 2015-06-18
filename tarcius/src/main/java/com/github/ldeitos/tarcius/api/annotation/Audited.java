package com.github.ldeitos.tarcius.api.annotation;

import static com.github.ldeitos.tarcius.configuration.Constants.STRING_RESOLVER_ID;
import static com.github.ldeitos.tarcius.configuration.TranslateType.STRING_VALUE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.ldeitos.tarcius.configuration.TranslateType;
import com.github.ldeitos.tarcius.qualifier.CustomResolver;

/**
 * Identify a parameter to be audited.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@Inherited
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Audited {

	/**
	 * @return Reference name to audited parameter.
	 */
	String auditRef();

	/**
	 * @return Translator type to be applied in translate parameter phase. This
	 *         configuration is mandatory over
	 *         {@link #customResolverQualifier()}, in ambiguity case between
	 *         this two parameters, the value configure here is considered.
	 */
	TranslateType translator() default STRING_VALUE;

	/**
	 * @return Format to be applied over parameter value if, and only if, the
	 *         translator type is {@link TranslateType#STRING_VALUE}, in other
	 *         way this configuration is unvalued. <br>
	 *         Used formatters: <br>
	 *         - {@link Date} and variants types uses {@link SimpleDateFormat}
	 *         formats;<br>
	 *         - Other types uses {@link String#format(String, Object...)}
	 *         method and patterns must be compatible with that.
	 */
	String format() default "";

	/**
	 * @return Qualifier to specify a {@link CustomResolver} to be applied to
	 *         parameter translation. Needs be configured <b>only if</b> the
	 *         {@link #translator()} equals to {@link TranslateType#CUSTOM}. <br>
	 * <br>
	 *         Case {@link #translator()} configuration is
	 *         {@link TranslateType#CUSTOM} but that is not correctly set to a
	 *         qualifier applied at valid implementation of
	 *         {@link CustomResolver} no errors occurs, but audit process is
	 *         interrupted in translate parameter phase and a warning log is
	 *         printed. <br>
	 * <br>
	 *         Case {@link #translator()} configuration is different of
	 *         {@link TranslateType#CUSTOM} but that is set to a qualifier, that
	 *         one will be unrecognized and not applied.
	 */
	CustomResolver customResolverQualifier() default @CustomResolver(STRING_RESOLVER_ID);
}
