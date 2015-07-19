package com.github.ldeitos.validation.impl.interpolator;

import static com.github.ldeitos.constants.Constants.DEFAULT_MESSAGE_FILE;
import static com.github.ldeitos.validation.impl.configuration.Configuration.getConfiguration;
import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import com.github.ldeitos.constants.Constants;
import com.github.ldeitos.util.ManualContext;
import com.github.ldeitos.validation.MessagesSource;
import com.github.ldeitos.validation.impl.configuration.ConfigInfoProvider;

/**
 * Default ExtendedValidation concrete implementation from
 * {@link MessagesSource}.<br/>
 * Recovery messages from ValidationMessages.properties <b>and</b> any other
 * file configured by {@link Constants#CONFIGURATION_FILE} or
 * {@link Constants#MESSAGE_FILES_SYSTEM_PROPERTY}.
 *
 * @author <a href="mailto:leandro.deitos@gmail.com">Leandro Deitos</a>
 *
 */
public class MultipleBundlesSource extends AbstractMessagesSource {

	/**
	 * Set of messages file names containing
	 * {@link Constants#DEFAULT_MESSAGE_FILE} and others configured file names,
	 * if any.
	 */
	private Set<String> bundleFiles = new HashSet<String>(asList(DEFAULT_MESSAGE_FILE));

	/**
	 * Cache to recovered bundles from file names.
	 */
	private Map<String, ResourceBundle> cache = new HashMap<String, ResourceBundle>();

	{
		ConfigInfoProvider configProvider = ManualContext.lookupCDI(ConfigInfoProvider.class);
		bundleFiles.addAll(getConfiguration(configProvider).getConfituredMessageFiles());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getInSource(String original, String key, Locale locale) {
		String msg = new String(original);

		for (String fileName : bundleFiles) {
			ResourceBundle resource = getBundle(fileName, locale);

			if (resource != null && resource.containsKey(key)) {
				msg = resource.getString(key);
				break;
			}
		}

		return msg;
	}

	/**
	 * @param fileName
	 *            Resource file name.
	 * @param locale
	 *            Locale to recover a resource file. May be null.
	 * @return {@link ResourceBundle} equivalent to file name and locale
	 *         solicited or null if does not exist.<br/>
	 *         If resource is correctly loaded from environment, the bundle is
	 *         cached to be recovered in future requests.
	 */
	private ResourceBundle getBundle(String fileName, Locale locale) {
		String cacheKey = parseCacheKey(fileName, locale);
		ResourceBundle resource;

		if (cache.containsKey(cacheKey)) {
			resource = cache.get(cacheKey);
		} else {
			try {
				if (locale == null) {
					resource = ResourceBundle.getBundle(fileName);
				} else {
					resource = ResourceBundle.getBundle(fileName, locale);
				}
				cache.put(cacheKey, resource);
			} catch (MissingResourceException ex) {
				resource = null;
			}
		}

		return resource;
	}

	/**
	 * @param fileName
	 *            File name.
	 * @param locale
	 *            Locale to desidered resource. May be null.
	 * @return Cache key string formed by file name concatenated, if locale is
	 *         not null, with "-" + locale display name.
	 */
	private String parseCacheKey(String fileName, Locale locale) {
		StringBuilder sb = new StringBuilder(fileName);

		if (locale != null) {
			sb.append("-").append(locale.getDisplayName());
		}

		return sb.toString();
	}
}
