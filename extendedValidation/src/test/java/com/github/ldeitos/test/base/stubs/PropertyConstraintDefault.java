package com.github.ldeitos.test.base.stubs;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ TYPE, METHOD, FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { PropertyValidatorDefaultImpl.class })
public @interface PropertyConstraintDefault {
	String message() default "{jakarta.validation.constraints.NotNull.message}";

	String[] messageParameters() default {};

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ TYPE, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		PropertyConstraintDefault[] value();
	}
}
