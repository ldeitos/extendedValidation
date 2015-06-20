package com.github.ldeitos.tarcius.audit.resolver;

import static com.github.ldeitos.tarcius.configuration.Constants.XML_RESOLVER_ID;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

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

	private Map<Class<?>, JAXBContext> contextCache = new HashMap<Class<?>, JAXBContext>();

	@Override
	protected boolean isOutputFormatted() {
		return false;
	}

	@Override
	protected <T> String marshal(T input) throws JAXBException, IOException {
		OutputStream outputStream = new ByteArrayOutputStream();
		Marshaller marshaller = getMarshaller(input);

		configure(marshaller);

		marshaller.marshal(input, outputStream);
		String data = outputStream.toString();

		outputStream.close();

		return data;
	}

	private <T> Marshaller getMarshaller(T input) throws JAXBException {
		Class<? extends Object> inputClass = input.getClass();
		JAXBContext context = contextCache.get(inputClass);

		if (context == null) {
			context = JAXBContext.newInstance(inputClass);
			contextCache.put(inputClass, context);
		}

		Marshaller marshaller = context.createMarshaller();

		return marshaller;
	}

	/**
	 * By default configure "jaxb.formatted.output" property.
	 *
	 * @param marshaller
	 *            JAXB {@link Marshaller} instance to be configured.
	 * @throws PropertyException
	 *             When errors occurs on get {@link Marshaller} property.
	 */
	protected void configure(Marshaller marshaller) throws PropertyException {
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, isOutputFormatted());
	}

}
