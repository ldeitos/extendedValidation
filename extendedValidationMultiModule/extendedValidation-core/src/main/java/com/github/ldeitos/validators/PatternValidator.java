package com.github.ldeitos.validators;

import javax.validation.ConstraintValidator;

import com.github.ldeitos.constraint.Pattern;

public interface PatternValidator extends 
	ConstraintValidator<Pattern, CharSequence> {
}
