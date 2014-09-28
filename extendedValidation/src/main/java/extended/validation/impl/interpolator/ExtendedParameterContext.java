package extended.validation.impl.interpolator;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableMap;

import java.lang.annotation.Annotation;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintTarget;
import javax.validation.ConstraintValidator;
import javax.validation.MessageInterpolator.Context;
import javax.validation.Payload;
import javax.validation.metadata.ConstraintDescriptor;

import extended.validation.Validator;

class ExtendedParameterContext implements
		Context {
	
	private final Context decorated;
	
	public ExtendedParameterContext(Context contexto) {
		decorated = contexto;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ConstraintDescriptor<?> getConstraintDescriptor() {
		return new ExtendedConstraintDescriptor(decorated.getConstraintDescriptor());
	}

	public Object getValidatedValue() {
		return decorated.getValidatedValue();
	}

	public <T> T unwrap(Class<T> type) {
		if ( type.isAssignableFrom( Validator.class ) ) {
			return type.cast( this );
		}
		
		throw new InvalidParameterException(format("Não é possível obter instância de %s.", type.getName()));
	}
	
	private static class ExtendedConstraintDescriptor<T extends Annotation> 
		implements ConstraintDescriptor<T>{
		private static final String PARAMETERS_KEY = "messageParameters";
		
		private static final Pattern PARAMETER_PATTERN = Pattern.compile("(.*)(:|=)(.*)");
		
		private static final int PARAMETER_KEY_GROUP = 1;
		
		private static final int PARAMETER_VALUE_GROUP = 3;

		private final ConstraintDescriptor<T> decorated;
		
		ExtendedConstraintDescriptor(ConstraintDescriptor<T> descriptor) {
			decorated = descriptor;
		}

		public T getAnnotation() {
			return decorated.getAnnotation();
		}

		public String getMessageTemplate() {
			return decorated.getMessageTemplate();
		}

		public Set<Class<?>> getGroups() {
			return decorated.getGroups();
		}

		public Set<Class<? extends Payload>> getPayload() {
			return decorated.getPayload();
		}

		public ConstraintTarget getValidationAppliesTo() {
			return decorated.getValidationAppliesTo();
		}

		public List<Class<? extends ConstraintValidator<T, ?>>> getConstraintValidatorClasses() {
			return decorated.getConstraintValidatorClasses();
		}

		public Map<String, Object> getAttributes() {
			Map<String, Object> atributes = 
					new HashMap<String, Object>(decorated.getAttributes());
			
			String[] parameters = getDeclaredParameters(atributes);
			
			for(int i = 0; i < parameters.length; i++){
				String par = parameters[i].trim();
				Matcher matcher = PARAMETER_PATTERN.matcher(par);
				String key, value;
				
				if(matcher.matches()){
					key = matcher.group(PARAMETER_KEY_GROUP);
					value = matcher.group(PARAMETER_VALUE_GROUP);					
				} else {
					key = String.valueOf(i);
					value = par;
				}
				
				atributes.put(key, value);
			}
			
			return unmodifiableMap(atributes);
		}

		private String[] getDeclaredParameters(Map<String, Object> atributes) {
			String[] declaredParameters = {};
			
			if(atributes.containsKey(PARAMETERS_KEY)){
				declaredParameters = (String[]) atributes.get(PARAMETERS_KEY);
			}
			
			return declaredParameters;
		}

		public Set<ConstraintDescriptor<?>> getComposingConstraints() {
			return decorated.getComposingConstraints();
		}

		public boolean isReportAsSingleViolation() {
			return decorated.isReportAsSingleViolation();
		}
	}
}
