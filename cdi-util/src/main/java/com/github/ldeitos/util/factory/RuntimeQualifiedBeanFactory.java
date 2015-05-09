package com.github.ldeitos.util.factory;

import java.lang.annotation.Annotation;

/**
 * Interface for factory that solves at runtime the bean qualifier.
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
public interface RuntimeQualifiedBeanFactory<B, Q extends Annotation, QV> {
	/**
	 * @param valueQualifier
	 *            Value used to solve at runtime the bean qualifier.
	 * @return Bean instance that qualified by qualifier type <b>Q</b>
	 *         identified by value type <b>QV</b>.
	 */
	B createBean(QV valueQualifier);
}