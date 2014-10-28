package com.github.ldeitos.constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.github.ldeitos.validators.EmptyValidator;

/**
 * The annotated element must be empty.
 * <p/>
 * Supported types are:
 * <ul>
 * <li>{@code CharSequence} (length of character sequence is evaluated)</li>
 * <li>{@code Collection} (collection size is evaluated)</li>
 * <li>{@code Map} (map size is evaluated)</li>
 * <li>Array (array length is evaluated)</li>
 * </ul>
 * <p/>
 * {@code null} elements are considered valid.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { EmptyValidator.class })
public @interface Empty {

	/**
	 * @return Parameter array to be interpolated at message. Parameters can be
	 *         informed in "key=value" or just "value" pattern.<br/>
	 *         e.g:<br/>
	 * <br/>
	 *         message="My {par1} message"<br/>
	 *         messageParameters = {"par1=parameterized"}<br/>
	 * <br/>
	 *         message="My {0} message"<br/>
	 *         messageParameters = {"parameterized"}
	 */
	String[] messageParameters() default {};

	String message() default "{com.github.ldeitos.Empty.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * Defines several {@link Empty} annotations on the same element.
	 *
	 * @see Empty
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@interface List {

		Empty[] value();
	}
}
