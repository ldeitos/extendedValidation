package com.github.ldeitos.validation.impl;

import static com.github.ldeitos.exception.ViolationException.throwNew;
import static java.lang.String.format;
import static org.apache.commons.collections15.CollectionUtils.collect;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

import org.apache.commons.collections15.Transformer;

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
	private static final Transformer<ConstraintViolation<?>, Message> toMessage = new Transformer<ConstraintViolation<?>, Message>() {
		@Override
		public Message transform(ConstraintViolation<?> arg0) {
			return new MessageImpl(arg0);
		}
	};

	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

	private javax.validation.Validator delegate = factory.getValidator();

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
		return new HashSet<Message>(collect(constraints, toMessage));
	}

	@Override
	public <T> Set<Message> validatePropertyBean(T object, String propertyName, Class<?>... groups) {
		Set<ConstraintViolation<T>> constraints = validateProperty(object, propertyName, groups);
		return new HashSet<Message>(collect(constraints, toMessage));
	}

	@Override
	public <T> Set<Message> validateValueBean(Class<T> beanType, String propertyName, Object value,
	    Class<?>... groups) {
		Set<ConstraintViolation<T>> constraints = validateValue(beanType, propertyName, value, groups);
		return new HashSet<Message>(collect(constraints, toMessage));
	}

	@Override
	public <T> void validateAndThrow(T object, Class<?>... groups) throws ViolationException {
		Set<Message> violations = validateBean(object, groups);

		if (isNotEmpty(violations)) {
			throwNew(format("Validated object [%s] has violations.", object), violations);
		}
	}

}