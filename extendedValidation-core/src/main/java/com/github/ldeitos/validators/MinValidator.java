package com.github.ldeitos.validators;

import javax.validation.ConstraintValidator;

import com.github.ldeitos.constraint.Min;

public interface MinValidator extends ConstraintValidator<Min, Object> {
	
}