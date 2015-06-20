package com.github.ldeitos.tarcius.audit.resolver;

import static com.github.ldeitos.tarcius.configuration.Constants.JSON_FORMATTED_RESOLVER_ID;

import javax.enterprise.context.ApplicationScoped;

import com.github.ldeitos.tarcius.api.ParameterResolver;
import com.github.ldeitos.tarcius.qualifier.CustomResolver;

/**
 * Default implementation to {@link ParameterResolver} to formatted JSON output.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@ApplicationScoped
@CustomResolver(JSON_FORMATTED_RESOLVER_ID)
public class DefaultFormattedJSONResolver extends DefaultJSONResolver {

	@Override
	protected boolean isOutputFormatted() {
		return true;
	}
}
