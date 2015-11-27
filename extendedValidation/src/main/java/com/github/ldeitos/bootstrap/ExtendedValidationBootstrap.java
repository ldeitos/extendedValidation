package com.github.ldeitos.bootstrap;

import static com.github.ldeitos.util.ManualContext.lookupCDI;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ldeitos.exception.InvalidConfigurationException;
import com.github.ldeitos.validation.impl.configuration.ConfigInfo;
import com.github.ldeitos.validation.impl.configuration.Configuration;

/**
 * ExtendedValidation engine bootstrap. Load ExtendedValidation configurations.
 *
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 * @since 0.9.3
 */
public class ExtendedValidationBootstrap implements Extension {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedValidationBootstrap.class);

	public void startup(@Observes AfterDeploymentValidation event, BeanManager bm) {
		if (Configuration.isUnloaded()) {
			ConfigInfo cp = lookupCDI(bm, ConfigInfo.class);
			try {
				LOGGER.info("Loading ExtendedValidation configurations.");
				Configuration.load(cp);
				LOGGER.info("ExtendedValidation configurations sucefully loaded.");
			} catch (InvalidConfigurationException e) {
				LOGGER.error("Error loading configuration. Audit process will not work.", e);
			}
		}
	}

}
