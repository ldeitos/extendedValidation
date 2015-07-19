package com.github.ldeitos.validation.impl.configuration;

import static com.github.ldeitos.constants.Constants.MESSAGE_FILES_SYSTEM_PROPERTY;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.collections15.CollectionUtils.collect;
import static org.apache.commons.collections4.CollectionUtils.forAllDo;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections4.Closure;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ldeitos.constants.Constants;
import com.github.ldeitos.exception.InvalidCDIContextException;
import com.github.ldeitos.exception.InvalidConfigurationException;
import com.github.ldeitos.util.ManualContext;
import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validation.ValidationClosure;
import com.github.ldeitos.validation.impl.configuration.dto.ConfigurationDTO;
import com.github.ldeitos.validation.impl.configuration.dto.MessageFileDTO;

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

	private MessagesSource messagesSource;

	private ValidationClosure validationClosure;

	private Configuration(ConfigInfoProvider configProvider) {
		configuration = ConfigurationLoader.loadConfiguration(configProvider);
	};

	/**
	 * @return Unique instance to {@link Configuration} to application use.
	 */
	public static Configuration getConfiguration(ConfigInfoProvider configProvider) {
		if (instance == null || configProvider.isInTest()) {
			instance = new Configuration(configProvider);
		}

		return instance;
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
		Transformer<String, String> toTrimString = new Transformer<String, String>() {
			@Override
			public String transform(String arg0) {
				return arg0.trim();
			}
		};
		String configuredFiles = System.getProperty(MESSAGE_FILES_SYSTEM_PROPERTY);
		Collection<String> fileNames = new ArrayList<String>();

		if (configuredFiles != null) {
			fileNames.addAll(collect(asList(configuredFiles.split(",")), toTrimString));
		}

		logTraceConfiguredFileNames(fileNames, "by system propertie");

		return fileNames;
	}

	private Collection<String> getMessageFilesFromXML() {
		Transformer<MessageFileDTO, String> toFileName = new Transformer<MessageFileDTO, String>() {
			@Override
			public String transform(MessageFileDTO arg0) {
				return arg0.toString();
			}
		};

		Collection<String> fileNames = collect(configuration.getMessageFiles(), toFileName);

		logTraceConfiguredFileNames(fileNames, "by validation.xml file");

		return fileNames;
	}

	private void logTraceConfiguredFileNames(Collection<String> fileNames, String string) {
		if (isNotEmpty(fileNames) && log.isTraceEnabled()) {
			final StringBuilder sbLog = new StringBuilder("Files configured ");
			sbLog.append(string).append(": [");
			Closure<? super String> buildLogMsg = new Closure<String>() {
				@Override
				public void execute(String arg0) {
					sbLog.append(arg0).append(", ");
				}
			};

			forAllDo(fileNames, buildLogMsg);

			int start = sbLog.lastIndexOf(",");
			int end = sbLog.lastIndexOf(" ");
			sbLog.replace(start, end, "]");

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
		} catch (InvalidCDIContextException e) {
			String warnMsg = format("Error to obtain [%s] reference by CDI Context. Cause: %s.",
				beanType.getCanonicalName(), e.getMessage());
			log.warn(warnMsg);

			bean = getByReflection(beanType);
		}

		return bean;
	}

	private <T> T getByReflection(Class<? extends T> beanType) {
		T bean = null;
		String msgError = format("Error to obtain %s instance from [%s] by reflection.",
			beanType.getInterfaces()[0].getSimpleName(), beanType.getCanonicalName());
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

	private <T> T getByCDIContext(Class<? extends T> type) {
		return ManualContext.lookupCDI(type);
	}
}
