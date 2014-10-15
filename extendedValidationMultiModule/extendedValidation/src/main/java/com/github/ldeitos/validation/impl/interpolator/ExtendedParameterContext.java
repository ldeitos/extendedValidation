package com.github.ldeitos.validation.impl.interpolator;

import static com.github.ldeitos.constants.Constants.PARAMETERS_FIELD_NAME;
import static com.github.ldeitos.constants.Constants.PARAMETER_PATTERN;
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

import com.github.ldeitos.constants.Constants;
import com.github.ldeitos.validation.Validator;

/**
 * A {@link Context} decorator to concrete BeanValidation API implementation in use.<br/>
 * Process a constraint content searching {@link Constants#PARAMETERS_FIELD_NAME} field.<br/>
 * Finding it, get all parameters content and add to attributes map in {@link ConstraintDescriptor}. 
 * 
 * @author <a href="mailto:leandro.deitos@gmail.com">Leandro Deitos</a>
 *
 */
class ExtendedParameterContext implements
		Context {
	
	/**
	 * Decorated {@link Context} instance.
	 */
	private final Context decorated;
	
	/**
	 * @param contexto
	 * 		{@link Context} instance to be decorated.
	 */
	public ExtendedParameterContext(Context contexto) {
		decorated = contexto;
	}

	
	/**
	 * Decorated {@link ConstraintDescriptor}.
	 * @see ExtendedConstraintDescriptor
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ConstraintDescriptor<?> getConstraintDescriptor() {
		return new ExtendedConstraintDescriptor(decorated.getConstraintDescriptor());
	}

	/**
	 * Delegate to decorated context.
	 */
	public Object getValidatedValue() {
		return decorated.getValidatedValue();
	}

	/**
	 * {@inheritDoc}.
	 */
	public <T> T unwrap(Class<T> type) {
		if ( type.isAssignableFrom( Validator.class ) ) {
			return type.cast( this );
		}
		
		throw new InvalidParameterException(format("Não é possível obter instância de %s.", type.getName()));
	}
	
	/**
	 * Decorator to {@link ConstraintDescriptor} to process constraint {@link Constants#PARAMETERS_FIELD_NAME} 
	 * field content.
	 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
	 *
	 * @param <T> Constraint type.
	 */
	private static class ExtendedConstraintDescriptor<T extends Annotation> 
		implements ConstraintDescriptor<T>{
		/**
		 * Patter compiled by {@link Constants#PARAMETER_PATTERN}
		 */
		private static final Pattern PARAMS_PATTERN = Pattern.compile(PARAMETER_PATTERN);
		
		/**
		 * Parameter key group index in regex {@link Constants#PARAMETER_PATTERN}.
		 */
		private static final int PARAMETER_KEY_GROUP = 1;
		
		/**
		 * Parameter value group index in regex {@link Constants#PARAMETER_PATTERN}.
		 */
		private static final int PARAMETER_VALUE_GROUP = 3;

		/**
		 * Decorated {@link ConstraintDescriptor} instance.
		 */
		private final ConstraintDescriptor<T> decorated;
		
		/**
		 * Constraint attribute map.
		 */
		private Map<String, Object> attributes;
		
		/**
		 * @param descriptor
		 * 		{@link ConstraintDescriptor} instance to be decorated.
		 */
		ExtendedConstraintDescriptor(ConstraintDescriptor<T> descriptor) {
			decorated = descriptor;
		}

		/**
		 * Delegate to {@link ConstraintDescriptor} decorated.
		 */
		public T getAnnotation() {
			return decorated.getAnnotation();
		}

		/**
		 * Delegate to {@link ConstraintDescriptor} decorated.
		 */
		public String getMessageTemplate() {
			return decorated.getMessageTemplate();
		}

		/**
		 * Delegate to {@link ConstraintDescriptor} decorated.
		 */
		public Set<Class<?>> getGroups() {
			return decorated.getGroups();
		}

		/**
		 * Delegate to {@link ConstraintDescriptor} decorated.
		 */
		public Set<Class<? extends Payload>> getPayload() {
			return decorated.getPayload();
		}

		/**
		 * Delegate to {@link ConstraintDescriptor} decorated.
		 */
		public ConstraintTarget getValidationAppliesTo() {
			return decorated.getValidationAppliesTo();
		}

		/**
		 * Delegate to {@link ConstraintDescriptor} decorated.
		 */
		public List<Class<? extends ConstraintValidator<T, ?>>> getConstraintValidatorClasses() {
			return decorated.getConstraintValidatorClasses();
		}

		/**
		 * Attribute map build by decorated {@link ConstraintDescriptor} attributes 
		 * aggregated by {@link Constants#PARAMETERS_FIELD_NAME} constraint content.
		 */
		public Map<String, Object> getAttributes() {
			if(attributes == null){
				attributes = buildExtendedParametersAttributes();
			}
			
			return attributes;
		}

		/**
		 * @return Map of constraint attributes and respective values aggregated by 
		 * {@link Constants#PARAMETERS_FIELD_NAME} constraint content.
		 */
		private Map<String, Object> buildExtendedParametersAttributes() {
			Map<String, Object> atributes = 
					new HashMap<String, Object>(decorated.getAttributes());
			
			String[] parameters = getDeclaredParameters(atributes);
			
			for(int i = 0; i < parameters.length; i++){
				String par = parameters[i].trim();
				Matcher matcher = PARAMS_PATTERN.matcher(par);
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

		/**
		 * @param atributes
		 * 		Original attribute map of constraint.
		 * @return
		 * 		Constraint {@link Constants#PARAMETERS_FIELD_NAME} field content, if any.
		 */
		private String[] getDeclaredParameters(Map<String, Object> atributes) {
			String[] declaredParameters = {};
			
			if(atributes.containsKey(PARAMETERS_FIELD_NAME)){
				declaredParameters = (String[]) atributes.get(PARAMETERS_FIELD_NAME);
			}
			
			return declaredParameters;
		}

		/**
		 * Delegate to {@link ConstraintDescriptor} decorated.
		 */
		public Set<ConstraintDescriptor<?>> getComposingConstraints() {
			return decorated.getComposingConstraints();
		}

		/**
		 * Delegate to {@link ConstraintDescriptor} decorated.
		 */
		public boolean isReportAsSingleViolation() {
			return decorated.isReportAsSingleViolation();
		}
	}
}
