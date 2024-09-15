package com.github.ldeitos.validators;

import jakarta.validation.ConstraintValidator;

import com.github.ldeitos.constraint.Max;

public interface MaxValidator extends ConstraintValidator<Max, Object> {
	
}