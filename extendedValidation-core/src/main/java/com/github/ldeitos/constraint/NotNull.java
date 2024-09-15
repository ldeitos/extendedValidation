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
import jakarta.validation.ReportAsSingleViolation;

/**
 * The annotated element must not be {@code null}.
 * Accepts any type.
 *
 * @author Emmanuel Bernard
 */
@jakarta.validation.constraints.NotNull
@Target( { METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation()
@Documented
public @interface NotNull {
	String message() default "{jakarta.validation.constraints.NotNull.message}";
	
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

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    /**
     * Defines several {@link NotNull} annotations on the same element.
     *
     * @see com.github.ldeitos.constraint.NotNull
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotNull[] value();
    }
}
