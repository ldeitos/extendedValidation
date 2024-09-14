package com.github.ldeitos.validators;

import jakarta.validation.ConstraintValidator;

import com.github.ldeitos.constraint.Pattern;

public interface PatternValidator extends 
	ConstraintValidator<Pattern, CharSequence> {
}
