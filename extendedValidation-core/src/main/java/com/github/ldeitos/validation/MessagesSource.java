package com.github.ldeitos.validation;

import java.util.Locale;

/**
 * Interface to messages sources.
 * Messages sources can be properties files, data bases, external modules or 
 * applications, mainframe routines or any else.
 * 
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public interface MessagesSource {
	
	/**
	 * @param key
	 * 		Message key to recover from source.
	 * @return
	 * 		Recovered message or, case isn't, the same solicited key.
	 */
	String getMessage(String key);
	
	/**
	 * 
	 * @param key
	 * 		Message key to recover from source.
	 * @param locale
	 * 		Locale to define message language. May be null.
	 * @return
	 * 		Recovered message or, case isn't, the same solicited key.
	 */
	String getMessage(String key, Locale locale);
}
