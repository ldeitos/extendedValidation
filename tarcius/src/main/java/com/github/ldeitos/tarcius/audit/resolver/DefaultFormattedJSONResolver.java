package com.github.ldeitos.tarcius.audit.resolver;

import static com.github.ldeitos.tarcius.configuration.Constants.JSON_FORMATTED_RESOLVER_ID;
import static org.eclipse.persistence.oxm.MediaType.APPLICATION_JSON;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.eclipse.persistence.jaxb.MarshallerProperties;

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
public class DefaultFormattedJSONResolver extends JaxbBasedResolver {

	@Override
	protected String getMediaType() {
		return APPLICATION_JSON.getMediaType();
	}

	@Override
	protected void configure(Marshaller marshaller) throws PropertyException {
		super.configure(marshaller);
		marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
	}

	@Override
	protected boolean isOutputFormatted() {
		return true;
	}
}
