package com.github.ldeitos.tarcius.configuration;

import javax.enterprise.util.AnnotationLiteral;

import com.github.ldeitos.tarcius.api.AuditDataDispatcher;
import com.github.ldeitos.tarcius.api.AuditDataFormatter;
import com.github.ldeitos.tarcius.qualifier.CustomResolver;

/**
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public interface Constants {
	/**
	 * Tarcius configuration file name: "META-INF/tarcius.xml".
	 */
	String CONFIGURATION_FILE = "META-INF/tarcius.xml";

	/**
	 * SimplePath to {@link AuditDataFormatter} in xml configuration file.
	 */
	String PATH_CONF_FORMATTER_CLASS = "formatter-class";

	/**
	 * SimplePath to {@link AuditDataDispatcher} in xml configuration file.
	 */
	String PATH_CONF_DISPATCHER_CLASS = "dispatcher-class";

	/**
	 * SimplePath to "interrupt on error" in xml configuration file.
	 */
	String PATH_CONF_INTERRUPT_ON_ERROR = "interrupt-on-error";

	String STRING_RESOLVER_ID = "com.github.ldeitos.tarcius.STRING";

	String FORMATTED_STRING_RESOLVER_ID = "com.github.ldeitos.tarcius.STRING.FORMATTED";

	String FORMATTED_DATE_RESOLVER_ID = "com.github.ldeitos.tarcius.DATE.FORMATTED";

	String XML_RESOLVER_ID = "com.github.ldeitos.tarcius.XML";

	String XML_FORMATTED_RESOLVER_ID = "com.github.ldeitos.tarcius.XML.formatted";

	String JSON_RESOLVER_ID = "com.github.ldeitos.tarcius.JSON";

	String JSON_FORMATTED_RESOLVER_ID = "com.github.ldeitos.tarcius.JSON.fomatted";

	String CUSTOM_RESOLVER_ID = "com.github.ldeitos.tarcius.CUSTOM";

	CustomResolver STRING_RESOLVER = new CustomResolverLiteral(STRING_RESOLVER_ID);

	CustomResolver FORMATTED_DATE_RESOLVER = new CustomResolverLiteral(FORMATTED_DATE_RESOLVER_ID);

	CustomResolver FORMATTED_STRING_RESOLVER = new CustomResolverLiteral(FORMATTED_STRING_RESOLVER_ID);

	CustomResolver XML_RESOLVER = new CustomResolverLiteral(XML_RESOLVER_ID);

	CustomResolver XML_FORMATTED_RESOLVER = new CustomResolverLiteral(XML_FORMATTED_RESOLVER_ID);

	CustomResolver JSON_RESOLVER = new CustomResolverLiteral(JSON_RESOLVER_ID);

	CustomResolver JSON_FORMATTED_RESOLVER = new CustomResolverLiteral(JSON_FORMATTED_RESOLVER_ID);

	CustomResolver CUSTOM_RESOLVER = new CustomResolverLiteral(CUSTOM_RESOLVER_ID);

	@SuppressWarnings("all")
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
