package extended.validation;

import static extended.validation.ConstraintSeverity.defaultValue;
import static extended.validation.ConstraintSeverity.error;
import static extended.validation.ConstraintSeverity.fatal;
import static extended.validation.ConstraintSeverity.info;
import static extended.validation.ConstraintSeverity.warn;

/**
 * 
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public enum Severity {
	ERROR(error()),
	INFO(info()),
	WARN(warn()), 
	FATAL(fatal());
	
	private Class<? extends ConstraintSeverity> cSeverity;
	
	private Severity(Class<? extends ConstraintSeverity> constraintSeverity){
		this.cSeverity = constraintSeverity;		
	}
	
	Class<? extends ConstraintSeverity> getConstraintSeverity() {
		return cSeverity;
	}
	
	public static Severity fromConstraintSeverity(Class<? extends ConstraintSeverity> constraintSeveritiry){
		Severity retorno = null;
		
		for(Severity value : values()){
			if(value.equals(constraintSeveritiry)){
				retorno = value;
				break;
			}
		}
		
		if(retorno == null){
			retorno = fromConstraintSeverity(defaultValue());
		}
			
		return retorno;				
	}
}
