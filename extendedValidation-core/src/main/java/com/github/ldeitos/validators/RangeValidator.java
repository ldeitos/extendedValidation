package com.github.ldeitos.validators;

import jakarta.validation.ConstraintValidator;

import com.github.ldeitos.constraint.Range;

public interface RangeValidator extends ConstraintValidator<Range, Object> {

}
