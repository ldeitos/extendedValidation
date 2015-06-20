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

/**
 * Abstract class to be implemented by {@link ParameterResolver} using JAXB to
 * convert parameter to String.
 * 
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
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

	/**
	 * By default configure "jaxb.formatted.output" and "eclipselink.media-type"
	 * properties.
	 *
	 * @param marshaller
	 *            JAXB {@link Marshaller} instance to be configured.
	 * @throws PropertyException
	 *             When errors occurs on get {@link Marshaller} property.
	 */
	protected void configure(Marshaller marshaller) throws PropertyException {
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, isOutputFormatted());
		marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, getMediaType());
	}

	/**
	 * @return Value to be assigned to "jaxb.formatted.output"
	 *         {@link Marshaller} property.
	 */
	protected abstract boolean isOutputFormatted();

	/**
	 * @return Value to be assigned to "eclipselink.media-type"
	 *         {@link Marshaller} property.
	 */
	protected abstract String getMediaType();
}
