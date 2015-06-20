package com.github.ldeitos.tarcius.audit.resolver;

import static com.github.ldeitos.tarcius.configuration.Constants.XML_RESOLVER_ID;
import static org.eclipse.persistence.oxm.MediaType.APPLICATION_XML;

import javax.enterprise.context.ApplicationScoped;

import com.github.ldeitos.tarcius.api.ParameterResolver;
import com.github.ldeitos.tarcius.qualifier.CustomResolver;

/**
 * Default implementation to {@link ParameterResolver} to unformatted XML
 * output.
 * 
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@ApplicationScoped
@CustomResolver(XML_RESOLVER_ID)
public class DefaultXMLResolver extends JaxbBasedResolver {

	@Override
	protected String getMediaType() {
		return APPLICATION_XML.getMediaType();
	}

	@Override
	protected boolean isOutputFormatted() {
		return false;
	}
}