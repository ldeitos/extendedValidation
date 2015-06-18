package com.github.ldeitos.tarcius.audit.resolver;

import static com.github.ldeitos.tarcius.configuration.Constants.XML_RESOLVER_ID;
import static org.eclipse.persistence.oxm.MediaType.APPLICATION_XML;

import javax.enterprise.context.ApplicationScoped;

import com.github.ldeitos.tarcius.qualifier.CustomResolver;

@ApplicationScoped
@CustomResolver(XML_RESOLVER_ID)
public class DefaultXMLResolver extends JaxbBasedResolver {

	@Override
	protected String getMediaType() {
		return APPLICATION_XML.getMediaType();
	}
}
