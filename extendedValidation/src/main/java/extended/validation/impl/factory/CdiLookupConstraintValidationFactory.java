package extended.validation.impl.factory;

import static javax.validation.Validation.byDefaultProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

import extended.util.ManualContext;

/**
 * 
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
@ApplicationScoped
public class CdiLookupConstraintValidationFactory implements
		ConstraintValidatorFactory {
	
	ConstraintValidatorFactory delegate = byDefaultProvider().configure().getDefaultConstraintValidatorFactory();

	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
		T constraintValidator = ManualContext.lookupCDI(key);
		
		if(constraintValidator == null) {
			constraintValidator = delegate.getInstance(key);
		}
		
		return constraintValidator;
	}

	public void releaseInstance(ConstraintValidator<?, ?> instance) {
		delegate.releaseInstance(instance);
	}
}
