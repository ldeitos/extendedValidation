package com.github.ldeitos.validation.impl.configuration;

import static com.github.ldeitos.constants.Constants.MESSAGE_FILES_SYSTEM_PROPERTY;
import static com.github.ldeitos.constants.Constants.PRESENTATION_MESSAGE_PATTERN;
import static com.github.ldeitos.constants.Constants.PRESENTATION_TEMPLATE_PATTERN;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.join;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.enterprise.inject.spi.CDI;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ldeitos.constants.Constants;
import com.github.ldeitos.exception.InvalidConfigurationException;
import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validation.ValidationClosure;
import com.github.ldeitos.validation.impl.configuration.dto.ConfigurationDTO;

/**
 * Class to access ExtendedValidation component configurations.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public class Configuration {
	public static final Pattern PRESENTATION_MESSAGE = compile(PRESENTATION_MESSAGE_PATTERN);

	public static final Pattern PRESENTATION_TEMPLATE = compile(PRESENTATION_TEMPLATE_PATTERN);

	private static Configuration instance;

	private Logger log = LoggerFactory.getLogger(Configuration.class);

	private ConfigurationDTO configuration;

	private MessagesSource messagesSource;

	private ValidationClosure validationClosure;

	private Configuration(ConfigInfoProvider configProvider) {
		configuration = ConfigurationLoader.loadConfiguration(configProvider);
	};

	/**
	 * @param configProvider
	 * 		Configuration information provider.
	 * 
	 * @return Unique instance to {@link Configuration} to application use.
	 */
	public static Configuration getConfiguration(ConfigInfoProvider configProvider) {
		if (isUnloaded() || configProvider.isInTest()) {
			instance = new Configuration(configProvider);

			if (!configProvider.isInTest()) {
				init(instance);
			}
		}

		return instance;
	}

	private static void init(Configuration instance) {
		instance.getConfiguredMessagesSource();
		instance.getConfiguredValidationClosure();
	}

	/**
	 * @return Message files names configured in
	 *         {@link Constants#CONFIGURATION_FILE}.
	 */
	public Collection<String> getConfituredMessageFiles() {
		ArrayList<String> messageFiles = new ArrayList<String>(getMessageFilesFromXML());

		messageFiles.addAll(getMessageFilesFromEnvironmentProperty());

		return unmodifiableList(messageFiles);
	}

	/**
	 * @return Message files names configured in
	 *         {@link Constants#MESSAGE_FILES_SYSTEM_PROPERTY}.
	 */
	private Collection<String> getMessageFilesFromEnvironmentProperty() {
		String configuredFiles = System.getProperty(MESSAGE_FILES_SYSTEM_PROPERTY);
		Collection<String> fileNames = new ArrayList<String>();

		if (configuredFiles != null) {
			fileNames.addAll(asList(configuredFiles.split(",")).stream().map(s -> s.trim()).collect(toList()));
		}

		logTraceConfiguredFileNames(fileNames, "by system propertie");

		return fileNames;
	}

	private Collection<String> getMessageFilesFromXML() {

		Collection<String> fileNames = configuration.getMessageFiles();

		logTraceConfiguredFileNames(fileNames, "by validation.xml file");

		return fileNames;
	}

	private void logTraceConfiguredFileNames(Collection<String> fileNames, String string) {
		if (isNotEmpty(fileNames) && log.isTraceEnabled()) {
			final StringBuilder sbLog = new StringBuilder("Files configured ");
			sbLog.append(string)
			.append(": [")
			.append(join(fileNames.toArray(), ","))
			.append("]");

			log.trace(sbLog.toString());
		}
	}

	/**
	 * @return {@link ValidationClosure} instance configured in
	 *         {@link Constants#CONFIGURATION_FILE} or component default if
	 *         isn't configured.
	 */
	@SuppressWarnings("unchecked")
	public ValidationClosure getConfiguredValidationClosure() {
		if (validationClosure == null) {
			Class<? extends ValidationClosure> beanType = null;
			String closureClassName = configuration.getValidationClosure();

			try {
				log.debug(format("Getting default ValidationSource instance from class %s.", closureClassName));
				beanType = (Class<? extends ValidationClosure>) Class.forName(closureClassName);
			} catch (ClassNotFoundException e) {
				log.error(format("Class %s not found in class path.", closureClassName), e);
				InvalidConfigurationException.throwNew(e.getMessage(), e);
			}

			validationClosure = resolveBean(beanType);
		}

		return validationClosure;
	}

	/**
	 * @return MessagesSource instance configured in
	 *         {@link Constants#CONFIGURATION_FILE} or component default if
	 *         isn't configured.
	 */
	@SuppressWarnings("unchecked")
	public MessagesSource getConfiguredMessagesSource() {
		if (messagesSource == null) {
			Class<? extends MessagesSource> beanType = null;
			String messageSourceClassName = configuration.getMessageSource();
			try {
				log.debug(format("Getting MessagesSource instance from class %s.", messageSourceClassName));
				beanType = (Class<? extends MessagesSource>) Class.forName(messageSourceClassName);
			} catch (ClassNotFoundException e) {
				log.error(format("Class %s not found in class path.", messageSourceClassName), e);
				InvalidConfigurationException.throwNew(e.getMessage(), e);
			}

			messagesSource = resolveBean(beanType);
			log.info(format("Using [%s] as message source.", beanType.getCanonicalName()));
		}

		return messagesSource;
	}

	/**
	 * @return Configured message presentation template.
	 * @since 0.9.4
	 */
	public String getMessagePresentationTemplate() {
		return configuration.getMessagePresentationTemplate();
	}

	public boolean showTemplate() {
		Matcher matcher = PRESENTATION_TEMPLATE.matcher(getMessagePresentationTemplate());
		return matcher.find();
	}

	public boolean showMessage() {
		Matcher matcher = PRESENTATION_MESSAGE.matcher(getMessagePresentationTemplate());
		return matcher.find();
	}

	private <T> T resolveBean(Class<? extends T> beanType) {
		T bean = null;

		try {
			bean = getByCDIContext(beanType);
			if (bean != null) {
				log.debug(format("Reference from [%s] obtained by CDI Context.", beanType.getCanonicalName()));
			} else {
				log.debug(format("Unable to get [%s] reference by CDI Context.", beanType.getCanonicalName()));
				bean = getByReflection(beanType);
			}
		} catch (Exception e) {
			String warnMsg = format("Error to obtain [%s] reference by CDI Context. Cause: %s.",
			    beanType.getCanonicalName(), e.getMessage());
			log.warn(warnMsg);

			bean = getByReflection(beanType);
		}

		return bean;
	}

	private <T> T getByReflection(Class<? extends T> beanType) {
		T bean = null;
		String interfaceName = extractInterfaceName(beanType);
		String msgError = format("Error to obtain %s instance from [%s] by reflection.",
		    interfaceName, beanType.getCanonicalName());
		try {
			log.warn("Trying by reflection...");
			bean = ConstructorUtils.invokeConstructor(beanType);
			log.debug(format("Reference from [%s] obtained by reflection.", beanType.getCanonicalName()));
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

	private <T> String extractInterfaceName(Class<? extends T> beanType) {
		String interfaceName = beanType.getSimpleName();
		
		if(isNotEmpty(beanType.getInterfaces())) {
			interfaceName = beanType.getInterfaces()[0].getSimpleName();			
		}
		
		return interfaceName;
	}

	private <T> T getByCDIContext(Class<? extends T> type) {
		return CDI.current().select(type).get();
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
		if (isUnloaded() || cp.isInTest()) {
			instance = new Configuration(cp);
		}
	}

	/**
	 * @return true if configuration is loaded.
	 *
	 * @since 0.9.3
	 */
	public static boolean isLoaded() {
		return !isUnloaded();
	}

	/**
	 * @return false if configuration is unloaded.
	 *
	 * @since 0.9.3
	 */
	public static boolean isUnloaded() {
		return instance == null;
	}
}