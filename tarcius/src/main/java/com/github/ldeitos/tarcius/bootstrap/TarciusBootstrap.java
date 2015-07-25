package com.github.ldeitos.tarcius.bootstrap;

import static com.github.ldeitos.util.ManualContext.lookupCDI;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ldeitos.tarcius.configuration.ConfigInfoProvider;
import com.github.ldeitos.tarcius.configuration.Configuration;
import com.github.ldeitos.tarcius.exception.InvalidConfigurationException;

/**
 * Tarcius engine bootstrap. Load Tarcius configurations.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @since 0.1.2
 */
public class TarciusBootstrap implements Extension {
	private static final Logger LOGGER = LoggerFactory.getLogger(TarciusBootstrap.class);

	public void startup(@Observes AfterDeploymentValidation event, BeanManager bm) {
		if (Configuration.isUnloaded()) {
			ConfigInfoProvider cp = lookupCDI(bm, ConfigInfoProvider.class);
			try {
				LOGGER.info("Loading Tarcius configurations.");
				Configuration.load(cp);
				LOGGER.info("Tarcius configurations sucefully loaded.");
			} catch (InvalidConfigurationException e) {
				LOGGER.error("Error loading configuration. Audit process will not work.", e);
			}
		}
	}

}
