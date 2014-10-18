package com.github.ldeitos.validation.impl.configuration;

import static com.github.ldeitos.constants.Constants.CONFIGURATION_FILE;
import static com.github.ldeitos.constants.Constants.DEFAULT_MESSAGE_SOURCE;
import static java.lang.String.format;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
			Enumeration<URL> configurations = ClassLoader.getSystemResources(CONFIGURATION_FILE);
			loadXMLFiles(configurations);
		} catch (IOException e1) {
			log.warn(format("Error on obtain %s files in class path: [%s]", CONFIGURATION_FILE,
				e1.getMessage()));
			log.warn("Loading by default configuration...");
			loadDefaultConfiguration();
		}
	}

	private static void loadXMLFiles(Enumeration<URL> configurations) throws IOException {
		int qtd = 0;
		while (configurations.hasMoreElements()) {
			qtd++;
			URL actual = configurations.nextElement();
			InputStream xmlInputStream = actual.openStream();

			try {
				String logDebug = format("Loading configuration file in %s", actual.getPath());
				log.debug(logDebug);
				ConfigurationDTO configurationDTO = getConfigurationDTO(xmlInputStream);

				if (configuration == null) {
					configuration = configurationDTO;
					continue;
				}

				configuration.merge(configurationDTO);
			} catch (JAXBException e) {
				loadDefaultConfiguration();
			}
		}
		log.info(format("Located configuration files: %d", qtd));
		log.info("ExtendedValidation configuration loaded.");
		traceConfiguration(configuration);
	}

	private static void loadDefaultConfiguration() {
		if (configuration == null) {
			configuration = getDefaultConfiguration();
		}
	}

	private static ConfigurationDTO getConfigurationDTO(InputStream xmlInputStream) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(ConfigurationDTO.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		ConfigurationDTO configuration = (ConfigurationDTO) jaxbUnmarshaller.unmarshal(xmlInputStream);
		traceConfiguration(configuration);

		return configuration;
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
