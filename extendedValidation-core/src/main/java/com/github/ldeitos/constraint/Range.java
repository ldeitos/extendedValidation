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

import com.github.ldeitos.validators.RangeValidator;

/**
 * The annotated element value range must be between the specified boundaries
 * (included).
 * <p/>
 * Supported types are:
 * <ul>
 * <li>{@code BigDecimal}</li>
 * <li>{@code BigInteger}</li>
 * <li>{@code byte}, {@code short}, {@code int}, {@code long}, and their
 * respective wrappers</li>
 * </ul>
 * <p/>
 * {@code null} elements are considered valid.
 *
 * @since 0.8.0
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { RangeValidator.class })
public @interface Range {

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

	String message() default "{com.github.ldeitos.Range.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * @return size the element must be higher or equal to
	 */
	long min() default 0;

	/**
	 * @return size the element must be lower or equal to
	 */
	long max() default Long.MAX_VALUE;

	/**
	 * Defines several {@link Range} annotations on the same element.
	 *
	 * @see Range
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@interface List {

		Range[] value();
	}
}
