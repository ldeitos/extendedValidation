package com.github.ldeitos.tarcius.configuration;

import static com.github.ldeitos.tarcius.configuration.Constants.PATH_CONF_DISPATCHER_CLASS;
import static com.github.ldeitos.tarcius.configuration.Constants.PATH_CONF_FORMATTER_CLASS;
import static com.github.ldeitos.tarcius.exception.InvalidConfigurationException.throwNew;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ldeitos.exception.InvalidCDIContextException;
import com.github.ldeitos.tarcius.api.AuditDataDispatcher;
import com.github.ldeitos.tarcius.api.AuditDataFormatter;
import com.github.ldeitos.tarcius.configuration.dto.ConfigurationDTO;
import com.github.ldeitos.tarcius.exception.InvalidConfigurationException;
import com.github.ldeitos.util.ManualContext;

/**
 * Class to access ExtendedValidation component configurations.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public class Configuration {
	private static Configuration instance;

	private Logger log = LoggerFactory.getLogger(Configuration.class);

	private ConfigurationDTO configuration;

	private AuditDataFormatter<?> formatter;

	private AuditDataDispatcher<?> dispatcher;

	/**
	 * @param configFile
	 *            Configuration file name.
	 */
	private Configuration(ConfigurationProvider cp) {
		configuration = ConfigurationLoader.loadConfiguration(cp);
	};

	/**
	 * @param cp
	 *            Configuration provider.
	 * @return Unique instance to {@link Configuration} to application use.
	 */
	public static Configuration getConfiguration(ConfigurationProvider cp) {
		if (instance == null || cp.isInTest()) {
			instance = new Configuration(cp);
		}

		return instance;
	}

	/**
	 * @return {@link AuditDataFormatter} instance configured in
	 *         {@link Constants#CONFIGURATION_FILE}.
	 * @throws InvalidConfigurationException
	 *             May occur when:<br>
	 *             - {@link AuditDataFormatter} class doesn't configured in
	 *             {@link Constants#CONFIGURATION_FILE};<br>
	 *             - {@link AuditDataFormatter} configured in
	 *             {@link Constants#CONFIGURATION_FILE} doesn't exist in
	 *             classpath;<br>
	 */
	@SuppressWarnings("unchecked")
	public AuditDataFormatter<?> getAuditDataFormatter() throws InvalidConfigurationException {
		validate(configuration);
		if (formatter == null) {
			Class<? extends AuditDataFormatter<?>> beanType = null;
			try {
				log.debug(format("Getting AuditDataFormatter instance from class %s.",
				    configuration.getFormatterClass()));
				beanType = (Class<? extends AuditDataFormatter<?>>) Class.forName(configuration
				    .getFormatterClass());
			} catch (ClassNotFoundException e) {
				log.error(format("Class %s not found in classpath.", configuration.getFormatterClass()), e);
				InvalidConfigurationException.throwNew(e.getMessage(), e);
			}

			formatter = resolveBean(beanType);
		}

		return formatter;
	}

	/**
	 * @return {@link AuditDataDispatcher} instance configured in
	 *         {@link Constants#CONFIGURATION_FILE}.
	 * @throws InvalidConfigurationException
	 *             May occur when:<br>
	 *             - {@link AuditDataDispatcher} class doesn't configured in
	 *             {@link Constants#CONFIGURATION_FILE};<br>
	 *             - {@link AuditDataDispatcher} configured in
	 *             {@link Constants#CONFIGURATION_FILE} doesn't exist in
	 *             classpath;<br>
	 */
	@SuppressWarnings("unchecked")
	public AuditDataDispatcher<?> getAuditDataDispatcher() throws InvalidConfigurationException {
		validate(configuration);
		if (dispatcher == null) {
			Class<? extends AuditDataDispatcher<?>> beanType = null;
			try {
				log.debug(format("Getting AuditDataDispatcher instance from class %s.",
				    configuration.getFormatterClass()));
				beanType = (Class<? extends AuditDataDispatcher<?>>) Class.forName(configuration
				    .getDispatcherClass());
			} catch (ClassNotFoundException e) {
				log.error(format("Class %s not found in classpath.", configuration.getDispatcherClass()), e);
				InvalidConfigurationException.throwNew(e.getMessage(), e);
			}

			dispatcher = resolveBean(beanType);
		}

		return dispatcher;
	}

	private <C> C resolveBean(Class<C> beanType) throws InvalidConfigurationException {
		C formatter = null;
		String className = beanType.getSimpleName();

		try {
			formatter = getByCDIContext(beanType);
			log.debug(format("Reference from [%s] obtained by CDI Context.", className));
		} catch (InvalidCDIContextException e) {
			String warnMsg = format("Error to obtain [%s] reference by CDI Context. Cause: %s.", className,
				e.getMessage());
			log.warn(warnMsg);
			log.warn("Trying by reflection...");

			formatter = getByReflection(beanType);
			log.debug(format("Reference from [%s] obtained by reflection.", className));
		}

		log.info(format("Using [%s] as message source.", className));

		return formatter;
	}

	private <C> C getByReflection(Class<C> beanType) throws InvalidConfigurationException {
		C bean = null;
		String msgError = format("Error to obtain %s instance from [%s] by reflection.",
			beanType.getSimpleName(), configuration.getFormatterClass());
		try {
			bean = ConstructorUtils.invokeConstructor(beanType);
		} catch (NoSuchMethodException e) {
			log.error(msgError, e);
			InvalidConfigurationException.throwNew(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(msgError, e);
			InvalidConfigurationException.throwNew(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.error(msgError, e);
			InvalidConfigurationException.throwNew(e.getMessage(), e);
		} catch (InstantiationException e) {
			log.error(msgError, e);
			InvalidConfigurationException.throwNew(e.getMessage(), e);
		}

		return bean;
	}

	private <C> C getByCDIContext(Class<C> type) {
		return ManualContext.lookupCDI(type);
	}

	private static void validate(ConfigurationDTO configuration) throws InvalidConfigurationException {
		String invalidMsg = "Invalid configuration: missing %s class definition (<%s> tag)";

		if (isBlank(configuration.getFormatterClass())) {
			throwNew(format(invalidMsg, "formatter", PATH_CONF_FORMATTER_CLASS));
		}

		if (isBlank(configuration.getDispatcherClass())) {
			throwNew(format(invalidMsg, "dispatcher", PATH_CONF_DISPATCHER_CLASS));
		}
	}

	public boolean mustInterruptOnError() {
		return configuration.isInterruptOnError();
	}

}
