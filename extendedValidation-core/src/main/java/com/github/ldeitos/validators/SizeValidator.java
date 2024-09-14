package com.github.ldeitos.validators;

import jakarta.validation.ConstraintValidator;

import com.github.ldeitos.constraint.Size;

public interface SizeValidator extends ConstraintValidator<Size, Object> {
	
}
