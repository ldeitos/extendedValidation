package com.github.ldeitos.validation.impl.configuration;

import static com.github.ldeitos.constants.Constants.CONFIGURATION_FILE;
import static com.github.ldeitos.constants.Constants.DEFAULT_MESSAGE_SOURCE;
import static com.github.ldeitos.constants.Constants.PATH_CONF_MESSAGE_FILE;
import static com.github.ldeitos.constants.Constants.PATH_CONF_MESSAGE_FILES;
import static com.github.ldeitos.constants.Constants.PATH_CONF_MESSAGE_SOURCE;
import static java.lang.String.format;

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
	 * @return DTO with configuration content from any
	 *         {@link Constants#CONFIGURATION_FILE} in application class path.
	 */
	static ConfigurationDTO loadConfiguration() {
		if (configuration == null) {
			load();
		}

		return configuration;
	}

	private static void load() {
		try {
			log.info(format("Loading configuration by %s files in class path.", CONFIGURATION_FILE));

			DefaultConfigurationBuilder confBuilder = new DefaultConfigurationBuilder(CONFIGURATION_FILE);
			confBuilder.load();
			loadFromXMLFiles(confBuilder);
		} catch (ConfigurationException e) {
			log.warn(format("Error on obtain %s files in class path: [%s]", CONFIGURATION_FILE,
				e.getMessage()));
			log.warn("Loading by default configuration...");
		}

		if (configuration == null) {
			loadDefaultConfiguration();
		}
	}

	private static void loadFromXMLFiles(DefaultConfigurationBuilder confBuilder) {
		if (!confBuilder.isEmpty()) {
			configuration = new ConfigurationDTO();

			if (confBuilder.containsKey(PATH_CONF_MESSAGE_SOURCE)) {
				configuration.setMessageSource(confBuilder.getString(PATH_CONF_MESSAGE_SOURCE));
			}

			for (HierarchicalConfiguration next : confBuilder.configurationsAt(PATH_CONF_MESSAGE_FILES)) {
				configuration.addMessageFile(next.getString(PATH_CONF_MESSAGE_FILE));
			}
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
		traceConfiguration(configuration);

		return configuration;
	}

	private static void traceConfiguration(ConfigurationDTO configuration) {
		log.trace(format("Configuration content: [%s]", configuration));
	}

}
