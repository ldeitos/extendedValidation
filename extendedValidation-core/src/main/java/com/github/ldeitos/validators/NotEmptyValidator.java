package com.github.ldeitos.validators;

import javax.validation.ConstraintValidator;

import com.github.ldeitos.constraint.NotEmpty;

public interface NotEmptyValidator extends ConstraintValidator<NotEmpty, Object> {

}
