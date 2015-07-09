package com.github.ldeitos.validators;

import javax.validation.ConstraintValidator;

import com.github.ldeitos.constraint.DecimalMax;

public interface MaxDecimalValidator extends ConstraintValidator<DecimalMax, Object> {
	
}