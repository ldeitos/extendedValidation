package com.github.ldeitos.validators;

import jakarta.validation.ConstraintValidator;

import com.github.ldeitos.constraint.Digits;

public interface DigitsValidator extends ConstraintValidator<Digits, Object> {
	
}
