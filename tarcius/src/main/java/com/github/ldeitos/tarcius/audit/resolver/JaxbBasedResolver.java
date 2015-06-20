package com.github.ldeitos.tarcius.audit.resolver;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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

	protected abstract <T> String marshal(T input) throws JAXBException, IOException;

	/**
	 * @return Value to be assigned to "jaxb.formatted.output"
	 *         {@link Marshaller} property.
	 */
	protected abstract boolean isOutputFormatted();

}
