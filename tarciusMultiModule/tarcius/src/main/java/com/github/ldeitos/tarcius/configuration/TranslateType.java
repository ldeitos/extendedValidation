package com.github.ldeitos.tarcius.configuration;

import static com.github.ldeitos.tarcius.configuration.Constants.CUSTOM_RESOLVER;
import static com.github.ldeitos.tarcius.configuration.Constants.JSON_RESOLVER;
import static com.github.ldeitos.tarcius.configuration.Constants.STRING_RESOLVER;
import static com.github.ldeitos.tarcius.configuration.Constants.XML_FORMATTED_RESOLVER;
import static com.github.ldeitos.tarcius.configuration.Constants.XML_RESOLVER;

import com.github.ldeitos.tarcius.api.ParameterResolver;
import com.github.ldeitos.tarcius.qualifier.CustomResolver;

/**
 * Translation types for the parameters to be audited.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public enum TranslateType {
	/**
	 * Default type, audited parameter value is resolved by Sting.valueOf();
	 */
	STRING_VALUE(STRING_RESOLVER),
	/**
	 * Audited parameter value is resolved to unformatted XML by JAXB API
	 */
	JAXB_XML(XML_RESOLVER),
	/**
	 * Audited parameter value is resolved to formatted XML by JAXB API
	 */
	JAXB_FORMATTED_XML(XML_FORMATTED_RESOLVER),
	/**
	 * Audited parameter value is resolved to unformatted JSON by JAXB API
	 */
	JAXB_JSON(JSON_RESOLVER),
	/**
	 * Audited parameter values is resolved by custom {@link ParameterResolver}
	 */
	CUSTOM(CUSTOM_RESOLVER);

	private CustomResolver resolverQualifier;

	private TranslateType(CustomResolver resolver) {
		resolverQualifier = resolver;
	}

	public CustomResolver getResolverQualifier() {
		return resolverQualifier;
	}

}
