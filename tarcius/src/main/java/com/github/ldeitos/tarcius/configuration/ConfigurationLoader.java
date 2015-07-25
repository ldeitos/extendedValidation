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
	 * @throws InvalidConfigurationException
	 */
	static ConfigurationDTO loadConfiguration(ConfigInfoProvider cp) throws InvalidConfigurationException {
		if (configuration == null || cp.isInTest()) {
			load(cp.getConfigFileName());
		}

		return configuration;
	}

	private static void load(String configFile) throws InvalidConfigurationException {
		try {
			log.info(format("Loading configuration by %s file in class path.", configFile));

			DefaultConfigurationBuilder confBuilder = new DefaultConfigurationBuilder(configFile);
			loadFromXMLFiles(confBuilder);
			traceConfiguration(configuration);
		} catch (Exception e) {
			String msg = format("Unable to on obtain %s file in class path: [%s], "
				+ "default configurations will be used.", CONFIGURATION_FILE, e.getMessage());
			log.warn(msg);
		}
	}

	private static void loadFromXMLFiles(DefaultConfigurationBuilder confBuilder)
	    throws ConfigurationException, InvalidConfigurationException {
		String fileName;
		configuration = new ConfigurationDTO();

		confBuilder.load();
		if (confBuilder.isEmpty()) {
			return;
		}

		if (confBuilder.containsKey(PATH_CONF_FORMATTER_CLASS)) {
			fileName = confBuilder.getString(PATH_CONF_FORMATTER_CLASS);
			log.debug(format("Configured AuditDataFormatter: [%s]", fileName));
			configuration.setFormatterClass(fileName);
		}

		if (confBuilder.containsKey(PATH_CONF_DISPATCHER_CLASS)) {
			fileName = confBuilder.getString(PATH_CONF_DISPATCHER_CLASS);
			log.debug(format("Configured AuditDataDispatcher: [%s]", fileName));
			configuration.setDispatcherClass(fileName);
		}

		if (confBuilder.containsKey(PATH_CONF_INTERRUPT_ON_ERROR)) {
			boolean interruptOnError = confBuilder.getBoolean(PATH_CONF_INTERRUPT_ON_ERROR);
			log.debug(format("Configured interruptOnError: [%s]", interruptOnError));
			configuration.setInterruptOnError(interruptOnError);
		}
	}

	private static void traceConfiguration(ConfigurationDTO configuration) {
		log.trace(format("Configuration content: [%s]", configuration));
	}

	public static void reset() {
		configuration = null;
	}

}
