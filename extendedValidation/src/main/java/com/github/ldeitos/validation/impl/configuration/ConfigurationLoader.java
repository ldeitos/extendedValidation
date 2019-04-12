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

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ldeitos.constants.Constants;
import com.github.ldeitos.validation.impl.configuration.dto.ConfigurationDTO;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

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

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		try (ScanResult scanResult = new ClassGraph().whitelistPaths(configPath).scan()) {
			scanResult.getResourcesWithLeafName(configFileName).forEachInputStream((r, is) -> {
				try {
					loadFromXMLFiles(inputFactory.createXMLEventReader(is));
				} catch (XMLStreamException e) {
					log.warn(format("Error on obtain %s files in class path: [%s]", configFileName, e.getMessage()));
					log.warn("Loading by default configuration...");
				}
			});
		}

		if (configuration == null) {
			loadDefaultConfiguration();
		}

		traceConfiguration(configuration);
	}

	private static void loadFromXMLFiles(XMLEventReader eventReader) throws XMLStreamException {
		if (configuration == null) {
			String config;
			String elementName;
			XMLEvent event;
			StartElement sElement;
			configuration = new ConfigurationDTO();
			while (eventReader.hasNext()) {
				event = eventReader.nextEvent();

				if (event.isStartElement()) {
					sElement = event.asStartElement();
					elementName = sElement.getName().getLocalPart();

					if (elementName.equals(PATH_CONF_VALIDATION_CLOSURE)) {
						event = eventReader.nextEvent();
						config = event.asCharacters().getData();
						log.debug(format("Configured ValidationClosure: [%s]", config));
						configuration.setValidationClosure(config);
						continue;
					}

					if (elementName.equals(PATH_CONF_MESSAGE_SOURCE)) {
						event = eventReader.nextEvent();
						config = event.asCharacters().getData();
						log.debug(format("Configured MessagesSource: [%s]", config));
						configuration.setMessageSource(config);
						continue;
					}

					if (elementName.equals(PATH_CONF_TEMPLATE_MESSAGE_PRESENTATION)) {
						event = eventReader.nextEvent();
						config = event.asCharacters().getData();
						verifyTemplateMessagePresentation(config);
						log.debug(format("Message presentation template: [%s]", config));
						configuration.setMessagePresentationTemplate(config);
						continue;
					}
					
					if (elementName.equals(PATH_CONF_MESSAGE_FILES)) {
						int openned = 0;
						do {
							event = eventReader.nextEvent();

							if(event.isCharacters() && !StringUtils.isAsciiPrintable(event.asCharacters().getData())){
								event = eventReader.nextEvent();
							}
							
							if (event.isStartElement()) {
								openned++;
								sElement = event.asStartElement();
								elementName = sElement.getName().getLocalPart();
								if (elementName.equals(PATH_CONF_MESSAGE_FILE)) {
									event = eventReader.nextEvent();
									config = event.asCharacters().getData();
									log.debug(format("Adding configured message file: [%s]", config));
									configuration.addMessageFile(config);
								}
							}
							
							if(event.isEndElement()) {
								openned--;
							}
						} while (openned > 0 && eventReader.hasNext());
					}
				}
			}
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