package com.github.ldeitos.util.factory;

import java.lang.annotation.Annotation;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * Abstract class for factory that solves at runtime the bean qualifier.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @param <B>
 *            Bean type to be created.
 * @param <Q>
 *            Qualifier type.
 * @param <QV>
 *            Value type used to solve at runtime the bean qualifier.
 */
public abstract class AbstractRuntimeQualifiedBeanFactory<B, Q extends Annotation, QV> implements
RuntimeQualifiedBeanFactory<B, Q, QV> {

	@Inject
	@Any
	private Instance<B> beans;

	@Override
	public final B createBean(QV valueQualifier) {
		Instance<B> typeBeans = beans.select(getQualifier(valueQualifier));

		return typeBeans.get();
	}

	/**
	 * @param valueQualifier
	 *            Value used to solve at runtime the bean qualifier.
	 *
	 * @return Instance of qualifier type Q with <b>valueQualifier</b> assigned.
	 */
	protected abstract Q getQualifier(QV valueQualifier);

}
