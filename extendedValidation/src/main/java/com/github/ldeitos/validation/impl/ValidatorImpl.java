package com.github.ldeitos.validation.impl;

import static com.github.ldeitos.exception.ViolationException.throwNew;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;
import jakarta.validation.metadata.BeanDescriptor;

import com.github.ldeitos.exception.ViolationException;
import com.github.ldeitos.qualifier.ExtendedValidator;
import com.github.ldeitos.validation.Message;
import com.github.ldeitos.validation.Validator;

/**
 * Extended validator concrete implementation.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@ExtendedValidator
public class ValidatorImpl implements Validator {

	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

	private jakarta.validation.Validator delegate = factory.getValidator();

	@Override
	public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
		return delegate.validate(object, groups);
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
		return delegate.validateProperty(object, propertyName, groups);
	}

	@Override
	public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName,
	    Object value, Class<?>... groups) {
		return delegate.validateValue(beanType, propertyName, value, groups);
	}

	@Override
	public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
		return delegate.getConstraintsForClass(clazz);
	}

	@Override
	public <T> T unwrap(Class<T> type) {
		if (type.isAssignableFrom(Validator.class)) {
			return type.cast(this);
		}

		throw new InvalidParameterException(format("Impossible to get %s instance.", type.getName()));
	}

	@Override
	public ExecutableValidator forExecutables() {
		return delegate.forExecutables();
	}

	@Override
	public <T> Set<Message> validateBean(T object, Class<?>... groups) {
		Set<ConstraintViolation<T>> constraints = validate(object, groups);
		return new HashSet<Message>(constraints.stream().map(MessageImpl::new).collect(toList()));
	}

	@Override
	public <T> Set<Message> validatePropertyBean(T object, String propertyName, Class<?>... groups) {
		Set<ConstraintViolation<T>> constraints = validateProperty(object, propertyName, groups);
		return new HashSet<Message>(constraints.stream().map(MessageImpl::new).collect(toList()));
	}

	@Override
	public <T> Set<Message> validateValueBean(Class<T> beanType, String propertyName, Object value,
	    Class<?>... groups) {
		Set<ConstraintViolation<T>> constraints = validateValue(beanType, propertyName, value, groups);
		return new HashSet<Message>(constraints.stream().map(MessageImpl::new).collect(toList()));
	}

	@Override
	public <T> void validateAndThrow(T object, Class<?>... groups) throws ViolationException {
		Set<Message> violations = validateBean(object, groups);

		if (isNotEmpty(violations)) {
			throwNew(format("Validated object [%s] has violations.", object), violations);
		}
	}

}