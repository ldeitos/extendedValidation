package com.github.ldeitos.util.factory;

import javax.enterprise.util.AnnotationLiteral;

public class StringFactory extends AbstractRuntimeQualifiedBeanFactory<StringInterface, QStringTest, String> {

	@Override
	protected QStringTest getQualifier(String valueQualifier) {
		return new QStringTestImpl(valueQualifier);
	}

	private class QStringTestImpl extends AnnotationLiteral<QStringTest> implements QStringTest {

		private static final long serialVersionUID = 1L;
		String value;

		QStringTestImpl(String value) {
			this.value = value;
		}

		@Override
		public String value() {
			return value;
		}

	}

}
