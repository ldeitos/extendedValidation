package com.github.ldeitos.tarcius.audit.resolver;

import static com.github.ldeitos.tarcius.configuration.Constants.JSON_RESOLVER_ID;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import com.github.ldeitos.tarcius.api.ParameterResolver;
import com.github.ldeitos.tarcius.qualifier.CustomResolver;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.sun.jersey.api.json.JSONMarshaller;

/**
 * Default implementation to {@link ParameterResolver} to unformatted JSON
 * output.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@ApplicationScoped
@CustomResolver(JSON_RESOLVER_ID)
public class DefaultJSONResolver extends JaxbBasedResolver {

	private Map<Class<?>, JSONJAXBContext> contextCache = new HashMap<Class<?>, JSONJAXBContext>();

	@Override
	protected boolean isOutputFormatted() {
		return false;
	}

	@Override
	protected <T> String marshal(T input) throws JAXBException, IOException {
		OutputStream outputStream = new ByteArrayOutputStream();
		JSONMarshaller marshaller = getMarshaller(input);

		configure(marshaller);

		marshaller.marshallToJSON(input, outputStream);
		String data = outputStream.toString();

		outputStream.close();

		return data;
	}

	private <T> JSONMarshaller getMarshaller(T input) throws JAXBException {
		Class<? extends Object> type = input.getClass();
		Class<?> types[] = { type };
		JSONJAXBContext context = contextCache.get(type);

		if (context == null) {
			context = new JSONJAXBContext(JSONConfiguration.mapped().build(), types);
			contextCache.put(type, context);
		}

		JSONMarshaller marshaller = context.createJSONMarshaller();

		return marshaller;
	}

	/**
	 * By default configure "formatted" property.
	 *
	 * @param marshaller
	 *            JAXB {@link Marshaller} instance to be configured.
	 * @throws PropertyException
	 *             When errors occurs on get {@link Marshaller} property.
	 */
	protected void configure(JSONMarshaller marshaller) throws PropertyException {
		marshaller.setProperty(JSONMarshaller.FORMATTED, isOutputFormatted());
	}
}
