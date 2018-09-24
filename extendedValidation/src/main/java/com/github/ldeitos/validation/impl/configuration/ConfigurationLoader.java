package com.github.ldeitos.validation.impl.configuration;

import static com.github.ldeitos.constants.Constants.DEFAULT_MESSAGE_SOURCE;
import static com.github.ldeitos.constants.Constants.PATH_CONF_MESSAGE_FILE;
import static com.github.ldeitos.constants.Constants.PATH_CONF_MESSAGE_FILES;
import static com.github.ldeitos.constants.Constants.PATH_CONF_MESSAGE_SOURCE;
import static com.github.ldeitos.constants.Constants.PATH_CONF_TEMPLATE_MESSAGE_PRESENTATION;
import static com.github.ldeitos.constants.Constants.PATH_CONF_VALIDATION_CLOSURE;
import static com.github.ldeitos.constants.Constants.PRESENTATION_MESSAGE_PATTERN;
import static java.lang.String.format;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ldeitos.constants.Constants;
import com.github.ldeitos.validation.impl.configuration.dto.ConfigurationDTO;

/**
 * Loader to {@link Constants#CONFIGURATION_FILE}.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
class ConfigurationLoader {
	private static ConfigurationDTO configuration;

	private static Logger log = LoggerFactory.getLogger(ConfigurationLoader.class);

	/**
	 * @param configProvider
	 * @return DTO with configuration content from any
	 *         {@link Constants#CONFIGURATION_FILE} in application class path.
	 */
	static ConfigurationDTO loadConfiguration(ConfigInfoProvider configProvider) {
		if (configuration == null || configProvider.isInTest()) {
			load(configProvider);
		}

		return configuration;
	}

	private static void load(ConfigInfoProvider configProvider) {
		String configFileName = configProvider.getConfigFileName();

		try {
			log.info(format("Loading configuration by %s files in class path.", configFileName));

			DefaultConfigurationBuilder confBuilder = new DefaultConfigurationBuilder(configFileName);
			confBuilder.load();
			loadFromXMLFiles(confBuilder);
		} catch (ConfigurationException e) {
			log.warn(format("Error on obtain %s files in class path: [%s]", configFileName, e.getMessage()));
			log.warn("Loading by default configuration...");
		}

		if (configuration == null) {
			loadDefaultConfiguration();
		}

		traceConfiguration(configuration);
	}

	private static void loadFromXMLFiles(DefaultConfigurationBuilder confBuilder) {
		if (!confBuilder.isEmpty()) {
			String config;
			configuration = new ConfigurationDTO();

			if (confBuilder.containsKey(PATH_CONF_VALIDATION_CLOSURE)) {
				config = confBuilder.getString(PATH_CONF_VALIDATION_CLOSURE);
				log.debug(format("Configured ValidationClosure: [%s]", config));
				configuration.setValidationClosure(config);
			}

			if (confBuilder.containsKey(PATH_CONF_MESSAGE_SOURCE)) {
				config = confBuilder.getString(PATH_CONF_MESSAGE_SOURCE);
				log.debug(format("Configured MessagesSource: [%s]", config));
				configuration.setMessageSource(config);
			}

			if (confBuilder.containsKey(PATH_CONF_TEMPLATE_MESSAGE_PRESENTATION)) {
				config = confBuilder.getString(PATH_CONF_TEMPLATE_MESSAGE_PRESENTATION);
				verifyTemplateMessagePresentation(config);
				log.debug(format("Message presentation template: [%s]", config));
				configuration.setMessagePresentationTemplate(config);
			}

			for (HierarchicalConfiguration nextConf : confBuilder.configurationsAt(PATH_CONF_MESSAGE_FILES)) {
				for (HierarchicalConfiguration nextFile : nextConf.configurationsAt(PATH_CONF_MESSAGE_FILE)) {
					config = nextFile.getRoot().getValue().toString();
					log.debug(format("Adding configured message file: [%s]", config));
					configuration.addMessageFile(config);
				}
			}
		}

	}

	private static void verifyTemplateMessagePresentation(String input) {
		Matcher matcher = Pattern.compile(PRESENTATION_MESSAGE_PATTERN).matcher(input);
		if (!matcher.find()) {
			log.warn(format("Message presentation template does not shows the message text, "
				+ "try add %s to pattern.", PRESENTATION_MESSAGE_PATTERN));
		}
	}

	private static void loadDefaultConfiguration() {
		if (configuration == null) {
			configuration = getDefaultConfiguration();
		}
	}

	private static ConfigurationDTO getDefaultConfiguration() {
		ConfigurationDTO configuration = new ConfigurationDTO();

		configuration.setMessageSource(DEFAULT_MESSAGE_SOURCE);
		log.info("Default configuration loaded.");

		return configuration;
	}

	private static void traceConfiguration(ConfigurationDTO configuration) {
		if (log.isTraceEnabled()) {
			log.trace(format("Configuration content: [%s]", configuration));
		}
	}

}