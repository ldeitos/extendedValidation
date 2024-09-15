package com.github.ldeitos.validation;

import java.util.Set;

import jakarta.validation.groups.Default;

import com.github.ldeitos.exception.ViolationException;

/**
 * Interface to ExtendedValidator implementation.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public interface Validator extends jakarta.validation.Validator {
	/**
	 * @param object
	 *            Object to validate
	 * @param groups
	 *            The group or list of groups targeted for validation (defaults
	 *            to {@link Default})
	 * @return Violations messages or empty if none.
	 */
	public <T> Set<Message> validateBean(T object, Class<?>... groups);

	/**
	 * @param object
	 *            Object to validate
	 * @param propertyName
	 *            Property to validate (i.e. field and getter constraints)
	 * @param groups
	 *            The group or list of groups targeted for validation (defaults
	 *            to {@link Default})
	 * @return Violations messages or empty if none.
	 */
	public <T> Set<Message> validatePropertyBean(T object, String propertyName, Class<?>... groups);

	/**
	 * @param beanType
	 *            The bean type
	 * @param propertyName
	 *            Property to validate
	 * @param value
	 *            Property value to validate
	 * @param groups
	 *            The group or list of groups targeted for validation (defaults
	 *            to {@link Default}).
	 * @return Violations messages or empty if none.
	 */
	public <T> Set<Message> validateValueBean(Class<T> beanType, String propertyName, Object value,
		Class<?>... groups);

	/**
	 * @param object
	 *            Object to validate
	 * @param groups
	 *            The group or list of groups targeted for validation (defaults
	 *            to {@link Default})
	 * @throws ViolationException
	 *             Occurs when object under validation has violations.
	 * @since 0.9.2
	 */
	public <T> void validateAndThrow(T object, Class<?>... groups) throws ViolationException;

}
