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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.ldeitos.validation.MessagesSource;

class MultipleBundlesSource implements MessagesSource {
	
	private static final Pattern PATTERN_BUNDLE_KEY = Pattern.compile("^(\\{|\\[)(.*)(\\}|\\])$");
	
	private static int BUNDLE_KEY_GROUP = 2;
	
	private Set<String> bundleFiles = new HashSet<String>(asList(DEFAULT_MESSAGE_FILE));
	
	private Map<String, ResourceBundle> cache = new HashMap<String, ResourceBundle>();
	
	{
		bundleFiles.addAll(getConfiguration().getConfituredMessageFiles());
	}

	public String getMessage(String key) {
		return getMessage(key, null);
	}
	
	public String getMessage(String key, Locale locale) {
		String msg = new String(key);		
		Matcher matcher = PATTERN_BUNDLE_KEY.matcher(key);
		
		if(matcher.matches()){
			for(String fileName : bundleFiles) {
				ResourceBundle resource = getBundle(fileName, locale);
				String bundleKey = matcher.group(BUNDLE_KEY_GROUP);
			
				if(resource != null && resource.containsKey(bundleKey)){
					msg = resource.getString(bundleKey);
					break;
				} 	
			}
		}
		
		return msg;
	}

	private ResourceBundle getBundle(String fileName, Locale locale) {
		String cacheKey = parseCacheKey(fileName, locale);
		ResourceBundle resource;
		
		if(cache.containsKey(cacheKey)){
			resource = cache.get(cacheKey);
		} else {
			try{
				if(locale ==  null){
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
	
	private String parseCacheKey(String fileName, Locale locale){
		StringBuilder sb = new StringBuilder(fileName);
				
		if(locale != null){
			sb.append("-").append(locale.getDisplayName());
		}
		
		return sb.toString();
	}

}
