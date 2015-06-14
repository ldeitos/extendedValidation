package com.github.ldeitos.validator.stubs;

import java.util.Iterator;
import java.util.Map.Entry;

import com.github.ldeitos.validators.AbstractExtendedValidator;
import com.github.ldeitos.validators.util.Path;

public class PathTestValidatorImpl extends AbstractExtendedValidator<PathTestConstraint, PathBean> {
	@Override
	public void initialize(PathTestConstraint constraintAnnotation) {
	}

	@Override
	public void doValidation(PathBean value) {
		if (value.getStringField() == null) {
			Path path = buildPath("stringField").getPath();
			addViolationWithDefaultTemplate(path, "par=defaultConstraintParameter");
		}

		Iterator<PathComposeBean> it = value.getList().iterator();
		PathComposeBean obj = null;
		for (int i = 0; it.hasNext(); obj = it.next()) {
			Path path = buildPath("stringField", i).getPath();
			if (obj.getComposeStringField() == null) {
				addViolationWithDefaultTemplate(path, "par=defaultConstraintParameter");
			}
		}

		for (Entry<String, PathComposeBean> entry : value.getMap().entrySet()) {
			obj = entry.getValue();
			Path path = buildPath("stringField", entry.getKey()).getPath();
			if (obj.getComposeStringField() == null) {
				addViolationWithDefaultTemplate(path, "par=defaultConstraintParameter");
			}
		}
	}
}