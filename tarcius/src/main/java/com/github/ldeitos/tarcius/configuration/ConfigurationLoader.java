package com.github.ldeitos.tarcius.configuration;

import static com.github.ldeitos.tarcius.configuration.Constants.CONFIGURATION_FILE;
import static com.github.ldeitos.tarcius.configuration.Constants.PATH_CONF_DISPATCHER_CLASS;
import static com.github.ldeitos.tarcius.configuration.Constants.PATH_CONF_FORMATTER_CLASS;
import static com.github.ldeitos.tarcius.configuration.Constants.PATH_CONF_INTERRUPT_ON_ERROR;
import static java.lang.String.format;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ldeitos.tarcius.configuration.dto.ConfigurationDTO;
import com.github.ldeitos.tarcius.exception.InvalidConfigurationException;

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
	 * @param cp
	 *            Configuration provider.
	 * @return DTO with configuration content from specified configuration file
	 *         in application class path.
	 */
	static ConfigurationDTO loadConfiguration(ConfigurationProvider cp) {
		if (configuration == null || cp.isInTest()) {
			load(cp.getConfigFileName());
		}

		return configuration;
	}

	private static void load(String configFile) {
		try {
			log.info(format("Loading configuration by %s files in class path.", configFile));

			DefaultConfigurationBuilder confBuilder = new DefaultConfigurationBuilder(configFile);
			confBuilder.load();
			loadFromXMLFiles(confBuilder);
			traceConfiguration(configuration);
		} catch (Exception e) {
			String msg = format("Error on obtain %s files in class path: [%s], maybe it's misplaced.",
				CONFIGURATION_FILE, e.getMessage());
			log.warn(msg);
		}
	}

	private static void loadFromXMLFiles(DefaultConfigurationBuilder confBuilder)
	    throws ConfigurationException, InvalidConfigurationException {
		configuration = new ConfigurationDTO();

		validateConfiguration(confBuilder);

		configuration.setFormatterClass(confBuilder.getString(PATH_CONF_FORMATTER_CLASS));
		configuration.setDispatcherClass(confBuilder.getString(PATH_CONF_DISPATCHER_CLASS));

		if (confBuilder.containsKey(PATH_CONF_INTERRUPT_ON_ERROR)) {
			configuration.setInterruptOnError(confBuilder.getBoolean(PATH_CONF_INTERRUPT_ON_ERROR));
		}
	}

	private static void validateConfiguration(DefaultConfigurationBuilder confBuilder)
	    throws InvalidConfigurationException {
		String invalidMsg = "Invalid configuration: missing %s class definition (<%s> tag)";
		throwIfTrue(confBuilder.isEmpty(), "Configuration file is apparently empty.");
		throwIfTrue(!confBuilder.containsKey(PATH_CONF_FORMATTER_CLASS),
		    format(invalidMsg, "formatter", PATH_CONF_FORMATTER_CLASS));
		throwIfTrue(!confBuilder.containsKey(PATH_CONF_DISPATCHER_CLASS),
		    format(invalidMsg, "dispatcher", PATH_CONF_DISPATCHER_CLASS));
	}

	private static void throwIfTrue(boolean toAssert, String msg) throws InvalidConfigurationException {
		if (toAssert) {
			InvalidConfigurationException.throwNew(msg);
		}
	}

	private static void traceConfiguration(ConfigurationDTO configuration) {
		log.trace(format("Configuration content: [%s]", configuration));
	}

}
