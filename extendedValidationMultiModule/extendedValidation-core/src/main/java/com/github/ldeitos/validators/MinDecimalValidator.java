package com.github.ldeitos.validators;

import javax.validation.ConstraintValidator;

import com.github.ldeitos.constraint.DecimalMin;

public interface MinDecimalValidator extends ConstraintValidator<DecimalMin, Object> {
	
}