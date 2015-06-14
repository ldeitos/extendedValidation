package com.github.ldeitos.tarcius.config;

import javax.enterprise.util.AnnotationLiteral;

import com.github.ldeitos.tarcius.qualifier.CustomResolver;

/**
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public interface ConfigConstants {
	String STRING_RESOLVER_ID = "com.github.ldeitos.tarcius.STRING";

	String FORMATTED_STRING_RESOLVER_ID = "com.github.ldeitos.tarcius.STRING.FORMATTED";

	String FORMATTED_DATE_RESOLVER_ID = "com.github.ldeitos.tarcius.DATE.FORMATTED";

	String XML_RESOLVER_ID = "com.github.ldeitos.tarcius.XML";

	String JSON_RESOLVER_ID = "com.github.ldeitos.tarcius.JSON";

	String CUSTOM_RESOLVER_ID = "com.github.ldeitos.tarcius.CUSTOM";

	CustomResolver STRING_RESOLVER = new CustomResolverLiteral(STRING_RESOLVER_ID);

	CustomResolver XML_RESOLVER = new CustomResolverLiteral(XML_RESOLVER_ID);

	CustomResolver JSON_RESOLVER = new CustomResolverLiteral(JSON_RESOLVER_ID);

	CustomResolver CUSTOM_RESOLVER = new CustomResolverLiteral(CUSTOM_RESOLVER_ID);

	static class CustomResolverLiteral extends AnnotationLiteral<CustomResolver> implements CustomResolver {
		private static final long serialVersionUID = 1L;

		private String value;

		public CustomResolverLiteral(String value) {
			this.value = value;
		}

		@Override
		public String value() {
			return value;
		}
	}
}
