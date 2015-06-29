package com.github.ldeitos.validators;

import static java.lang.String.format;

import java.lang.annotation.Annotation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.LeafNodeBuilderCustomizableContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validation.impl.interpolator.PreInterpolator;
import com.github.ldeitos.validators.util.ConstraintBuilderAdapter;
import com.github.ldeitos.validators.util.NodeBuilderCustomizableContextAdapter;
import com.github.ldeitos.validators.util.Path;
import com.github.ldeitos.validators.util.PathBuilder;

/**
 * Abstraction to provide easy way to create validator with multiple violation
 * registers.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @param <A>
 *            Constraint annotation
 * @param <T>
 *            Type in validation.
 *
 * @since 0.8.0
 */
public abstract class AbstractExtendedValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

	private Logger log = LoggerFactory.getLogger(AbstractExtendedValidator.class);

	private ThreadLocal<Boolean> validMap = new ThreadLocal<Boolean>();

	private ThreadLocal<ConstraintValidatorContext> contextMap = new ThreadLocal<ConstraintValidatorContext>();

	@Inject
	private PreInterpolator preInterpolator;

	private PathBuilder pathBuilder;

	/**
	 * {@inheritDoc}<br>
	 */
	@Override
	public final boolean isValid(T value, ConstraintValidatorContext context) {
		contextMap.set(context);
		validMap.set(true);

		doValidation(value);

		Boolean valid = validMap.get();

		if (!valid) {
			String logMsg = "[%s] value are invalided by [%s] validator call.";
			log.info(format(logMsg, value, this.getClass().getName()));
		}

		release();

		return valid;
	}

	private void release() {
		validMap.remove();
		contextMap.remove();
	}

	/**
	 * Extension point to implement validation code. <br/>
	 * This code is invoked during API
	 * {@link #isValid(Object, ConstraintValidatorContext)} call and violation
	 * must be registered by any methods below:
	 * <ul>
	 * <li>{@link #addViolation(String, String...)}</li>
	 * <li>{@link #addViolationWithDefaultTemplate(String...)}</li>
	 * </ul>
	 *
	 * @param value
	 *            Value in validation.
	 *
	 * @since 0.8.0
	 */
	public abstract void doValidation(T value);

	/**
	 * Makes value on validation invalid and add a violation with default
	 * message template, defined in Constraint annotation, and parameters to
	 * interpolate.
	 *
	 * @param msgParameters
	 *            Parameters to be interpolated in message violation.<br>
	 *            Can be informed in "value" pattern, to be interpolated in
	 *            indexed parameter like "My {0} message" or in "key=value"
	 *            pattern, to be interpolated in defined parameter like
	 *            "My {par} message".
	 *
	 * @since 0.8.0
	 */
	protected void addViolationWithDefaultTemplate(String... msgParameters) {
		ConstraintValidatorContext context = contextMap.get();
		String msg = context.getDefaultConstraintMessageTemplate();
		String msgInterpolated = preInterpolator.interpolate(msg, msgParameters);

		log.debug("Adding violation with default template.");
		doTraceLog(msgParameters);

		makeInvalid();

		context.buildConstraintViolationWithTemplate(msgInterpolated).addConstraintViolation();
	}

	/**
	 * Makes value on validation invalid and add a violation with default
	 * message template, defined in Constraint annotation, and parameters to
	 * interpolate.
	 *
	 * @param path
	 *            Full {@link Path} to origin of violation in object being
	 *            validated.
	 *
	 * @param msgParameters
	 *            Parameters to be interpolated in message violation.<br>
	 *            Can be informed in "value" pattern, to be interpolated in
	 *            indexed parameter like "My {0} message" or in "key=value"
	 *            pattern, to be interpolated in defined parameter like
	 *            "My {par} message".
	 *
	 * @since 0.9.0
	 */
	protected void addViolationWithDefaultTemplate(Path atPath, String... msgParameters) {
		ConstraintValidatorContext context = contextMap.get();
		String msg = context.getDefaultConstraintMessageTemplate();
		String msgInterpolated = preInterpolator.interpolate(msg, msgParameters);

		log.debug("Adding violation with default template.");
		doTraceLog(msgParameters);

		makeInvalid();

		ConstraintViolationBuilder cvBuilder = context.buildConstraintViolationWithTemplate(msgInterpolated);

		if (atPath.isRootIterablePath()) {
			buildIterablePathAndAddConstraint(cvBuilder, atPath);
		} else {
			buildPath(cvBuilder, atPath).addConstraintViolation();
		}
	}

	/**
	 * Makes value on validation invalid and add a violation with default
	 * message template, defined in Constraint annotation, and parameters to
	 * interpolate.
	 *
	 * @param pathBuilder
	 *            {@link PathBuilder} to build {@link Path} reference.
	 *
	 * @param msgParameters
	 *            Parameters to be interpolated in message violation.<br>
	 *            Can be informed in "value" pattern, to be interpolated in
	 *            indexed parameter like "My {0} message" or in "key=value"
	 *            pattern, to be interpolated in defined parameter like
	 *            "My {par} message".
	 *
	 * @since 0.9.0
	 */
	protected void addViolationWithDefaultTemplate(PathBuilder pathBuilder, String... msgParameters) {
		addViolationWithDefaultTemplate(pathBuilder.getPath(), msgParameters);
	}

	/**
	 * Makes value on validation invalid and add a violation with informed
	 * message template and parameters to interpolate.
	 *
	 * @param msgTemplate
	 *            Message template can be:<br>
	 *            - Just message text, like "My message";<br>
	 *            - Message text with parameters, like "My {0} message" or
	 *            "My {par} message";<br>
	 *            - Message key to get message in parameterized
	 *            {@link MessagesSource}, like {my.message.key}.
	 * @param msgParameters
	 *            Parameters to be interpolated in message violation.<br>
	 *            Can be informed in "value" pattern, to be interpolated in
	 *            indexed parameter like "My {0} message" or in "key=value"
	 *            pattern, to be interpolated in defined parameter like
	 *            "My {par} message".
	 *
	 * @since 0.8.0
	 */
	protected void addViolation(String msgTemplate, String... msgParameters) {
		ConstraintValidatorContext context = contextMap.get();
		String msgInterpolated = preInterpolator.interpolate(msgTemplate, msgParameters);

		log.debug(format("Adding violation with [%s] template.", msgTemplate));
		doTraceLog(msgParameters);

		makeInvalid();

		context.buildConstraintViolationWithTemplate(msgInterpolated).addConstraintViolation();
	}

	/**
	 * Makes value on validation invalid and add a violation with informed
	 * message template and parameters to interpolate.
	 *
	 * @param path
	 *            Full {@link Path} to origin of violation in object being
	 *            validated.
	 *
	 * @param msgTemplate
	 *            Message template can be:<br>
	 *            - Just message text, like "My message";<br>
	 *            - Message text with parameters, like "My {0} message" or
	 *            "My {par} message";<br>
	 *            - Message key to get message in parameterized
	 *            {@link MessagesSource}, like {my.message.key}.
	 * @param msgParameters
	 *            Parameters to be interpolated in message violation.<br>
	 *            Can be informed in "value" pattern, to be interpolated in
	 *            indexed parameter like "My {0} message" or in "key=value"
	 *            pattern, to be interpolated in defined parameter like
	 *            "My {par} message".
	 *
	 * @since 0.9.0
	 */
	protected void addViolation(Path path, String msgTemplate, String... msgParameters) {
		ConstraintValidatorContext context = contextMap.get();
		String msgInterpolated = preInterpolator.interpolate(msgTemplate, msgParameters);

		log.debug(format("Adding violation with [%s] template.", msgTemplate));
		doTraceLog(msgParameters);

		makeInvalid();

		ConstraintViolationBuilder vBuilder = context.buildConstraintViolationWithTemplate(msgInterpolated);

		if (path.isRootIterablePath()) {
			buildIterablePathAndAddConstraint(vBuilder, path);
		} else {
			buildPath(vBuilder, path).addConstraintViolation();
		}
	}

	/**
	 * Makes value on validation invalid and add a violation with informed
	 * message template and parameters to interpolate.
	 *
	 * @param pathBuilder
	 *            {@link PathBuilder} to build {@link Path} reference.
	 *
	 * @param msgTemplate
	 *            Message template can be:<br>
	 *            - Just message text, like "My message";<br>
	 *            - Message text with parameters, like "My {0} message" or
	 *            "My {par} message";<br>
	 *            - Message key to get message in parameterized
	 *            {@link MessagesSource}, like {my.message.key}.
	 * @param msgParameters
	 *            Parameters to be interpolated in message violation.<br>
	 *            Can be informed in "value" pattern, to be interpolated in
	 *            indexed parameter like "My {0} message" or in "key=value"
	 *            pattern, to be interpolated in defined parameter like
	 *            "My {par} message".
	 *
	 * @since 0.9.0
	 */
	protected void addViolation(PathBuilder path, String msgTemplate, String... msgParameters) {
		addViolation(pathBuilder.getPath(), msgTemplate, msgParameters);
	}

	private void doTraceLog(String[] msgParameters) {
		if (log.isTraceEnabled()) {
			for (int i = 0; i < msgParameters.length; i++) {
				String parameter = msgParameters[i];
				log.trace(format("Parameter #%d: %s", i, parameter));
			}
		}
	}

	private void makeInvalid() {
		if (validMap.get()) {
			ConstraintValidatorContext context = contextMap.get();

			validMap.set(false);
			context.disableDefaultConstraintViolation();
			log.debug("Value marked as invalid.");
		}
	}

	/**
	 * Builder to describe a simple {@link Path} to violation origin.
	 *
	 * Usage examples:
	 *
	 * <pre>
	 * //assuming the following domain model
	 * public class Address {
	 *     public String getStreet() { ... }
	 *     public Country getCountry() { ... }
	 * }
	 *
	 * //From a class level constraint on Address
	 * //Build a constraint violation on the default path + "street"
	 * //i.e. the street property of Address
	 * buildPath("street");
	 *
	 * @param path
	 * 		String representation of path to property to register violation.
	 * @param index
	 * 		Index to indexed collection content to registered violation.
	 * @return
	 * 		{@link PathBuilder} to build a {@link Path} reference.
	 *
	 * @since 0.9.0
	 */
	protected PathBuilder buildPath(String path) {
		if (pathBuilder == null) {
			pathBuilder = new PathBuilder();
		}

		return pathBuilder.add(path);
	}

	/**
	 * Builder to describe a mapped {@link Path} to violation origin.
	 *
	 * Usage examples:
	 *
	 * <pre>
	 * //assuming the following domain model
	 * public class User {
	 *     public Map<String, Address> getAddresses() { ... }
	 * }
	 * 
	 * public class Address {
	 *     public String getStreet() { ... }
	 *     public Country getCountry() { ... }
	 * }
	 * 
	 * public class Country {
	 *     public String getName() { ... }
	 * }
	 * 
	 * //From a class level constraint on User
	 * //Build a constraint violation on the default path + addresses["home"].country.name
	 * //i.e. property "country.name" on the object stored under "home" in the map
	 * buildPath("addresses", "home").add("country").add("name")<br>
	 * or <br>
	 * buildPath("addresses[home].country.name")<br><br>
	 * 
	 * <b>P.S.:</b> A full path build like buildPath("addresses[home].country.name"), when refers a map,
	 * only can be used when a map key is a String, in other way, uses a fluent model, like
	 * buildPath("addresses", aObject).add("country").add("name").
	 * 
	 * 
	 * @param path
	 * 		String representation of path to property to register violation.
	 * @param index
	 * 		Index to indexed collection content to registered violation.
	 * @return
	 * 		{@link PathBuilder} to build a {@link Path} reference.
	 *
	 * @since 0.9.1
	 */
	protected PathBuilder buildPath(String path, Object key) {
		if (pathBuilder == null) {
			pathBuilder = new PathBuilder();
		}

		return pathBuilder.add(path, key);
	}

	/**
	 * Return a {@link Path} to property level mapped content.<br>
	 * Usage examples:
	 *
	 * <pre>
	 * //assuming the following domain model
	 * public class User {
	 *     public Map<String, Address> getAddresses() { ... }
	 * }
	 * 
	 * public class Address {
	 *     public String getStreet() { ... }
	 *     public Country getCountry() { ... }
	 * }
	 * 
	 * //From a property-level constraint on User.addresses
	 * //Build a constraint violation on the default path + the bean stored
	 * //under the "home" key on map:
	 *  atKey("home")
	 * 
	 * @param index
	 * 		Index to indexed collection content to registered violation.
	 * @return
	 * 		Correspondent {@link Path}.
	 *
	 * @since 0.9.1
	 */
	protected Path atKey(Object key) {
		if (pathBuilder == null) {
			pathBuilder = new PathBuilder();
		}

		return pathBuilder.addAtKey(key).getPath();
	}

	/**
	 * Builder to describe a indexed {@link Path} to violation origin.
	 *
	 * Usage examples:
	 *
	 * <pre>
	 * //assuming the following domain model
	 * public class User {
	 *     public List<Address> getAddresses() { ... }
	 * }
	 * 
	 * public class Address {
	 *     public String getStreet() { ... }
	 *     public Country getCountry() { ... }
	 * }
	 * 
	 * //From a class level constraint on User
	 * //Build a constraint violation on the default path + addresses["home"].country.name
	 * //i.e. property "country.name" on the object stored under index 2 in the list
	 * buildPath("addresses", 2).add("country").add("name")<br>
	 * or <br>
	 * buildPath("addresses(2).country.name")<br><br>
	 * 
	 * 
	 * @param path
	 * 		String representation of path to property to register violation.
	 * @param index
	 * 		Index to indexed collection content to registered violation.
	 * @return
	 * 		{@link PathBuilder} to build a {@link Path} reference.
	 *
	 * @since 0.9.0
	 */
	protected PathBuilder buildPath(String path, Integer index) {
		if (pathBuilder == null) {
			pathBuilder = new PathBuilder();
		}

		return pathBuilder.add(path, index);
	}

	/**
	 * Return a {@link Path} to property level indexed collection content.<br>
	 * Usage examples:
	 *
	 * <pre>
	 * //assuming the following domain model
	 * public class User {
	 *     public List<Address> getAddresses() { ... }
	 * }
	 * 
	 * public class Address {
	 *     public String getStreet() { ... }
	 *     public Country getCountry() { ... }
	 * }
	 * 
	 * //From a property-level constraint on User.addresses
	 * //Build a constraint violation on the default path + the bean stored
	 * //under the index 2 on list:
	 *  atIndex(2)
	 * 
	 * @param index
	 * 		Index to indexed collection content to registered violation.
	 * @return
	 * 		Correspondent {@link Path}.
	 *
	 * @since 0.9.0
	 */
	protected Path atIndex(Integer index) {
		if (pathBuilder == null) {
			pathBuilder = new PathBuilder();
		}

		return pathBuilder.addAtIndex(index).getPath();
	}

	private ConstraintBuilderAdapter buildPath(ConstraintViolationBuilder cvBuilder, Path path) {
		ConstraintBuilderAdapter constraintBuilderAdapter = new NodeBuilderCustomizableContextAdapter(
			cvBuilder.addPropertyNode(path.getPath()));

		while (path.hasNext()) {
			path = path.getNext();
			constraintBuilderAdapter = constraintBuilderAdapter.addPropertyNode(path);
		}

		return constraintBuilderAdapter;
	}

	private void buildIterablePathAndAddConstraint(ConstraintViolationBuilder vBuilder, Path path) {
		LeafNodeBuilderCustomizableContext leafNodeBCxt = vBuilder.addBeanNode();

		if (path.hasKey()) {
			leafNodeBCxt.inIterable().atKey(path.getKey()).addConstraintViolation();
		} else if (path.hasIndex()) {
			leafNodeBCxt.inIterable().atIndex(path.getIndex()).addConstraintViolation();
		}
	}
}
