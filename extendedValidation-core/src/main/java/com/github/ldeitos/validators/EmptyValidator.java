package com.github.ldeitos.validators;

import jakarta.validation.ConstraintValidator;

import com.github.ldeitos.constraint.Empty;

public interface EmptyValidator extends ConstraintValidator<Empty, Object> {

}
