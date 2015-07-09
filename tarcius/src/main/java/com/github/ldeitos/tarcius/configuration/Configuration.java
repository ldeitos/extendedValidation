package com.github.ldeitos.tarcius.configuration;

import static com.github.ldeitos.util.ManualContext.lookupCDI;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.lang.reflect.InvocationTargetException;

import javax.enterprise.inject.UnsatisfiedResolutionException;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ldeitos.exception.InvalidCDIContextException;
import com.github.ldeitos.tarcius.api.AuditDataDispatcher;
import com.github.ldeitos.tarcius.api.AuditDataFormatter;
import com.github.ldeitos.tarcius.configuration.dto.ConfigurationDTO;
import com.github.ldeitos.tarcius.configuration.factory.TarciusComponentFactory;
import com.github.ldeitos.tarcius.exception.InvalidConfigurationException;

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
	 * @throws InvalidConfigurationException
	 */
	private Configuration(ConfigInfoProvider cp) throws InvalidConfigurationException {
		configuration = ConfigurationLoader.loadConfiguration(cp);
	};

	/**
	 * @param cp
	 *            Configuration provider.
	 * @return Unique instance to {@link Configuration} to application use.
	 *
	 * @throws InvalidConfigurationException
	 *             In case of invalid configurations at file
	 *             {@link Constants#CONFIGURATION_FILE} or this is inexistent.
	 */
	public static Configuration getConfiguration(ConfigInfoProvider cp) throws InvalidConfigurationException {
		load(cp);
		return instance;
	}

	/**
	 * Load Tarcius configuration.
	 *
	 * @param cp
	 *            Configuration provider.
	 * @throws InvalidConfigurationException
	 *             In case of invalid configurations at file
	 *             {@link Constants#CONFIGURATION_FILE} or this is inexistent.
	 * @since 0.1.2
	 */
	public static void load(ConfigInfoProvider cp) throws InvalidConfigurationException {
		if (isUnladed() || cp.isInTest()) {
			instance = new Configuration(cp);
		}
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
	public AuditDataFormatter<?> getAuditDataFormatter() throws InvalidConfigurationException {
		if (formatter == null) {
			@SuppressWarnings("rawtypes")
			Class<? extends AuditDataFormatter> beanType = null;
			try {
				beanType = getBeanType(AuditDataFormatter.class, configuration.getFormatterClass());
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
	public AuditDataDispatcher<?> getAuditDataDispatcher() throws InvalidConfigurationException {
		if (dispatcher == null) {
			@SuppressWarnings("rawtypes")
			Class<? extends AuditDataDispatcher> beanType = null;
			try {
				beanType = getBeanType(AuditDataDispatcher.class, configuration.getDispatcherClass());
			} catch (ClassNotFoundException e) {
				log.error(format("Class %s not found in classpath.", configuration.getDispatcherClass()), e);
				InvalidConfigurationException.throwNew(e.getMessage(), e);
			}

			dispatcher = resolveBean(beanType);
		}

		return dispatcher;
	}

	@SuppressWarnings("unchecked")
	private <T> Class<T> getBeanType(Class<T> interfaceType, String definedClass)
		throws ClassNotFoundException {
		Class<T> beanType = interfaceType;

		if (isNotBlank(definedClass)) {
			log.debug(format("Getting %s configurated class %s.", interfaceType.getSimpleName(),
				configuration.getFormatterClass()));
			beanType = (Class<T>) Class.forName(definedClass);
		}

		return beanType;
	}

	private <C> C resolveBean(Class<C> beanType) throws InvalidConfigurationException {
		C bean = null;
		String className = beanType.getSimpleName();

		try {
			bean = getByCDIContext(beanType);
			className = bean.getClass().getSimpleName();
			log.debug(format("Reference from [%s] obtained by CDI Context.", className));
		} catch (InvalidCDIContextException e) {
			logWarnObtainCDIReference(className, e);
		} catch (UnsatisfiedResolutionException e) {
			logWarnObtainCDIReference(className, e);
		} finally {
			if (bean == null) {
				log.warn("Trying by reflection...");

				bean = getByReflection(beanType);
				log.debug(format("Reference from [%s] obtained by reflection.", className));
			}
		}

		log.info(format("Using [%s] as message source.", className));

		return bean;
	}

	private void logWarnObtainCDIReference(String className, Exception e) {
		String warnMsg = format("Error to obtain [%s] reference by CDI Context. Cause: %s.", className,
			e.getMessage());
		log.warn(warnMsg);
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
		C bean = lookupCDI(type);

		if (bean == null) {
			TarciusComponentFactory factory = lookupCDI(TarciusComponentFactory.class);
			bean = factory.get(type);
		}

		return bean;
	}

	public boolean mustInterruptOnError() {
		return configuration.isInterruptOnError();
	}

	/**
	 * @return true if configuration is loaded.
	 *
	 * @since 0.1.2
	 */
	public static boolean isLoaded() {
		return !isUnladed();
	}

	/**
	 * @return false if configuration is unloaded.
	 *
	 * @since 0.1.2
	 */
	public static boolean isUnladed() {
		return instance == null;
	}

	public static void reset() {
		Configuration.instance = null;
		ConfigurationLoader.reset();
	}

}
