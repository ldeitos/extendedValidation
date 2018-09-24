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
import javax.validation.ReportAsSingleViolation;

@javax.validation.constraints.Future
@Target( { METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation()
@Documented
public @interface Future {
	String message() default "{javax.validation.constraints.Future.message}";
	
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
     * Defines several {@link Future} annotations on the same element.
     *
     * @see com.github.ldeitos.constraint.Future
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Future[] value();
    }
}
