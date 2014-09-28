package extended.validation;

import javax.validation.ConstraintViolation;


/**
 * 
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public interface Message {
	Severity getSeverity();
	
	String getMessage();
	
	ConstraintViolation<?> getOriginConstraint();
}
