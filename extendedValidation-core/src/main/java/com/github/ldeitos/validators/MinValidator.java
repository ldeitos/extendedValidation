package com.github.ldeitos.validators;

import jakarta.validation.ConstraintValidator;

import com.github.ldeitos.constraint.Min;

public interface MinValidator extends ConstraintValidator<Min, Object> {
	
}