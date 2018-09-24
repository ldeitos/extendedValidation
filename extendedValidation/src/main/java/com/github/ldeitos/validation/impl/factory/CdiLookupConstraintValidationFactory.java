package com.github.ldeitos.validation.impl.factory;

import static java.lang.String.format;
import static javax.validation.Validation.byDefaultProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@ApplicationScoped
public class CdiLookupConstraintValidationFactory implements ConstraintValidatorFactory {

	private Logger log = LoggerFactory.getLogger(CdiLookupConstraintValidationFactory.class);

	private ConstraintValidatorFactory delegate = byDefaultProvider().configure()
		.getDefaultConstraintValidatorFactory();

	@Override
	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
		T constraintValidator = null;

		log.trace(format("Getting reference to validator [%s].", key.getName()));

		try {
			constraintValidator = CDI.current().select(key).get();

			if (constraintValidator == null) {
				log.debug(format("Unable to get reference to validator [%s] by CDI.", key.getName()));
				log.debug("Trying by default ConstraintValidatorFactory.");
				constraintValidator = delegate.getInstance(key);
			}
		} catch (Exception e) {
			log.debug(format("Error trying get reference to validator [%s] by CDI.", key.getName()));
			log.debug("Trying by default ConstraintValidatorFactory.");
			constraintValidator = delegate.getInstance(key);
		}

		return constraintValidator;
	}

	@Override
	public void releaseInstance(ConstraintValidator<?, ?> instance) {
		delegate.releaseInstance(instance);
	}
}
