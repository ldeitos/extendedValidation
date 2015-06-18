package com.github.ldeitos.tarcius.audit.resolver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.eclipse.persistence.jaxb.MarshallerProperties;

import com.github.ldeitos.tarcius.api.ParameterResolver;
import com.github.ldeitos.tarcius.exception.ParameterValueResolveException;

public abstract class JaxbBasedResolver implements ParameterResolver<Object> {

	@Override
	public String resolve(Object input) {
		try {
			return marshal(input);
		} catch (JAXBException e) {
			throw new ParameterValueResolveException("Unable to resolve parameter value", e);
		} catch (IOException e) {
			throw new ParameterValueResolveException("Unable to resolve parameter value", e);
		}
	}

	private <T> String marshal(T input) throws JAXBException, IOException {
		JAXBContext jc = JAXBContext.newInstance(input.getClass());
		OutputStream outputStream = new ByteArrayOutputStream();
		Marshaller marshaller = jc.createMarshaller();

		configure(marshaller);

		marshaller.marshal(input, outputStream);
		String data = outputStream.toString();

		outputStream.close();

		return data;
	}

	protected void configure(Marshaller marshaller) throws PropertyException {
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, getMediaType());
	}

	protected abstract String getMediaType();
}
