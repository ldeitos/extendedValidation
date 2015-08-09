package com.github.ldeitos.restutil;

import static java.lang.String.format;
import static java.util.regex.Pattern.matches;
import static org.apache.commons.collections4.CollectionUtils.collect;
import static org.apache.commons.collections4.CollectionUtils.find;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.beans.ParameterDescriptor;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;

/**
 * Descriptor to parsed parameters obtained from {@link ParametersParser}.
 *
 * @author <a href="mailto:leandro.deitos@gmail.com">Leandro Deitos</a>
 *
 */
public class ParamDescriptor {
	private static final Transformer<ParamDescriptor, String> TO_PARAM_NAME = new Transformer<ParamDescriptor, String>() {
		public String transform(ParamDescriptor input) {
			return input.getParamName();
		}
	};

	private String paramName;

	private List<ParamDescriptor> innerParams = new ArrayList<ParamDescriptor>();

	ParamDescriptor() {
		super();
		paramName = EMPTY;
	}

	public ParamDescriptor(String paramName) {
		super();

		if (isBlank(paramName) || matches("\\W", paramName)) {
			throw new InvalidParameterException(format("paramName [%s] must be a valid alphanumeric word.",
			    paramName));
		}

		this.paramName = paramName;
	}

	/**
	 * If current parameter is complex, return correspondent inner
	 * {@link ParameterDescriptor}, or null if {@link #isSimple()}.
	 *
	 * @param paramName
	 *            Name from parameter to obtain {@link ParamDescriptor}.
	 *
	 * @return Correspondent paramName {@link ParameterDescriptor}.
	 *
	 * @see #isComplex()
	 * @see #isSimple()
	 */
	public ParamDescriptor getInnerParam(final String paramName) {
		return find(innerParams, new Predicate<ParamDescriptor>() {
			public boolean evaluate(ParamDescriptor object) {
				return object.getParamName().equals(paramName);
			}
		});
	}

	/**
	 * @return If current parameter is complex, return list of
	 *         {@link ParamDescriptor} from inner parameters.
	 *
	 * @see #isComplex()
	 * @see #isSimple()
	 */
	public List<ParamDescriptor> getInnerParams() {
		return innerParams;
	}

	/**
	 * @return Parameter name.
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * @return True if parameter contain other parameters.
	 */
	public boolean isComplex() {
		return !isSimple();
	}

	/**
	 * @return True if parameter does not contain other parameters.
	 */
	public boolean isSimple() {
		return innerParams.isEmpty();
	}

	/**
	 * @return True if parameter does not contain name.
	 */
	public boolean isRoot() {
		return isBlank(paramName);
	}

	@Override
	public String toString() {
		return isRoot() ? "root" : paramName;
	}

	public String[] getInnerParamsNames() {
		List<String> innerParamsNames = new ArrayList<String>(collect(innerParams, TO_PARAM_NAME));
		return innerParamsNames.toArray(new String[innerParamsNames.size()]);
	}

}
