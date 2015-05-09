package com.github.ldeitos.util.factory;

import javax.enterprise.util.AnnotationLiteral;

public class IntegerFactory extends AbstractRuntimeQualifiedBeanFactory<IntegerInterface, QIntegerTest, Integer> {

	@Override
	protected QIntegerTest getQualifier(Integer valueQualifier) {
		return new QStringTestImpl(valueQualifier);
	}

	private class QStringTestImpl extends AnnotationLiteral<QIntegerTest> implements QIntegerTest {

		private static final long serialVersionUID = 1L;
		Integer value;

		QStringTestImpl(Integer value) {
			this.value = value;
		}

		@Override
		public int value() {
			return value;
		}

	}

}
