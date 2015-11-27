package com.github.ldeitos.validation.impl.configuration;

import com.github.ldeitos.exception.InvalidCDIContextException;
import com.github.ldeitos.util.ManualContext;

/**
 * Provider to {@link ConfigInfo} class. To unit test use only.
 *
 * @author <a href="mailto:leandro.deitos@gmail.com">Leandro Deitos</a>
 *
 * @since 1.0.RC3
 */
public class ConfigInfoProvider {
	private static ConfigInfo configInfo;

	public static ConfigInfo getConfigInfo() {
		try {
			configInfo = ManualContext.lookupCDI(ConfigInfo.class);
		} catch (InvalidCDIContextException e) {
			if (configInfo == null) {
				configInfo = new ConfigInfo();
			}
		}

		return configInfo;
	}
}
