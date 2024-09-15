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

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.github.ldeitos.validators.DigitsValidator;

/**
 * The annotated element must be a number within accepted range
 * Supported types are:
 * <ul>
 *     <li>{@code BigDecimal}</li>
 *     <li>{@code BigInteger}</li>
 *     <li>{@code CharSequence}</li>
 *     <li>{@code byte}, {@code short}, {@code int}, {@code long}, and their respective
 *     wrapper types</li>
 * </ul>
 * <p>
 * {@code null} elements are considered valid.
 *
 * @author Emmanuel Bernard
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { DigitsValidator.class })
public @interface Digits {

	/**
     * @return Parameter array to be interpolated at message. Parameters can be informed in
     * "key=value" or just "value" pattern.<br>
     * e.g:<br>
     * <br>
     * message="My {par1} message"<br>
     * messageParameters = {"par1=parameterized"}<br>
     * <br>
     * message="My {0} message"<br>
     * messageParameters = {"parameterized"}
     */
	String[] messageParameters() default {};
	
	String message() default "{jakarta.validation.constraints.Digits.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * @return maximum number of integral digits accepted for this number
     */
    int integer();

    /**
     * @return maximum number of fractional digits accepted for this number
     */
    int fraction();

    /**
     * Defines several {@link Digits} annotations on the same element.
     *
     * @see Digits
     */
    @Target( { METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        Digits[] value();
    }
}
