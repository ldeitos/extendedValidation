package com.github.ldeitos.validation.impl.configuration;

import static com.github.ldeitos.constants.Constants.DEFAULT_MESSAGE_SOURCE;
import static com.github.ldeitos.constants.Constants.PRESENTATION_MESSAGE_PATTERN;
import static java.lang.String.format;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

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
		String configPath = configProvider.getConfigPath();
		String configFileName = configProvider.getConfigFileName();

		log.info(format("Loading configuration by %s/%s files in class path.", configPath, configFileName));

		try (InputStream is = ConfigurationLoader.class.getClassLoader().getResourceAsStream(configProvider.getConfigFileLocation())) {
				try {
					loadFromXMLFiles(is);
				} catch (JAXBException e) {
					log.warn(format("Error on bind XML content from %s files in class path: [%s]", configFileName, e.getMessage()));
					log.warn("Loading by default configuration...");
				}
		} catch (IOException ex){
			log.warn(format("Error on obtain %s files in class path: [%s]", configFileName, ex.getMessage()));
			log.warn("Loading by default configuration...");
		}

		if (configuration == null) {
			loadDefaultConfiguration();
		}

		traceConfiguration(configuration);
	}

	private static void loadFromXMLFiles(InputStream is) throws JAXBException {
		if (configuration == null) {

			JAXBContext jaxbContext;
      		jaxbContext = JAXBContext.newInstance(ConfigurationDTO.class);
      		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			configuration = (ConfigurationDTO) jaxbUnmarshaller.unmarshal(is);

			verifyTemplateMessagePresentation(configuration.getMessagePresentationTemplate());
		}
	}

	private static void verifyTemplateMessagePresentation(String input) {
		Matcher matcher = Pattern.compile(PRESENTATION_MESSAGE_PATTERN).matcher(input);
		if (!matcher.find()) {
			log.warn(
					format("Message presentation template does not shows the message text, " + "try add %s to pattern.",
							PRESENTATION_MESSAGE_PATTERN));
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