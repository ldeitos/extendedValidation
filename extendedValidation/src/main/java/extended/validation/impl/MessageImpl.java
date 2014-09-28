package extended.validation.impl;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import javax.validation.metadata.ConstraintDescriptor;

import extended.validation.ConstraintSeverity;
import extended.validation.Message;
import extended.validation.Severity;

public class MessageImpl implements Message {
	
	private Severity severity;
	
	private String message;
	
	private ConstraintViolation<?> originConstraint;
	
	MessageImpl(ConstraintViolation<?> originConstraint){
		this.originConstraint = originConstraint;
		message = originConstraint.getMessage();
		severity = getSeverity(originConstraint.getConstraintDescriptor());
	}

	@SuppressWarnings("unchecked")
	private <T extends Annotation>Severity getSeverity(ConstraintDescriptor<T> descriptor) {
		Severity severity = Severity.ERROR;
		
		for(Class<? extends Payload> payload : descriptor.getPayload()){
			if(payload.isAssignableFrom(ConstraintSeverity.class)) {
				severity = Severity.fromConstraintSeverity((Class<ConstraintSeverity>)payload);
				break;
			}
		}
		
		return severity;
	}

	public Severity getSeverity() {
		return severity;
	}

	public String getMessage() {
		return message;
	}

	public ConstraintViolation<?> getOriginConstraint() {
		return originConstraint;
	}

}
