package com.github.ldeitos.validation.impl.interpolator;

import static com.github.ldeitos.constants.Constants.MESSAGE_FILES_SYSTEM_PROPERTY;
import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

public class MultipleBundlesSourceTest {
	private static final String OTHER_MESSAGE_FILE = "otherValidationMessages";
	
	private static final String DEFAULT_MESSAGE_KEY = "{validation.default.test}";
	
	private static final String OTHER_MESSAGE_KEY = "{validation.other.test}";
	
	private static final String DEFAULT_MESSAGE = "Default file message.";
	
	private static final String DEFAULT_LOCALIZED_MESSAGE = "Localized default file message.";
	
	private static final String OTHER_MESSAGE = "Other file message.";
	
	private static final String OTHER_LOCALIZED_MESSAGE = "Localized other file message.";
	
	private static final String NO_KEY_TEXT = "{Parameter} - any text with {parameter}.";
	
	private static final String KEY_NOT_MAPPED = "{any.key}";
	
	@Test
	public void testDefaultMessageFile(){
		MultipleBundlesSource source = new MultipleBundlesSource();
		String msg = source.getMessage(DEFAULT_MESSAGE_KEY);
		
		assertEquals(DEFAULT_MESSAGE, msg);
	}
	
	@Test
	public void testDefaultMessageFileLocalized(){
		Locale locale = new Locale("en_US");
		MultipleBundlesSource source = new MultipleBundlesSource();
		String msg = source.getMessage(DEFAULT_MESSAGE_KEY, locale);
		
		assertEquals(DEFAULT_LOCALIZED_MESSAGE, msg);
	}
	
	
	@Test
	public void testOtherMessageFile(){
		System.setProperty(MESSAGE_FILES_SYSTEM_PROPERTY, OTHER_MESSAGE_FILE);
		
		MultipleBundlesSource source = new MultipleBundlesSource();
		String msg = source.getMessage(OTHER_MESSAGE_KEY);
		
		assertEquals(OTHER_MESSAGE, msg);
		
		System.clearProperty(MESSAGE_FILES_SYSTEM_PROPERTY);
	}
	
	@Test
	public void testOtherMessageFileLocalized() {
		System.setProperty(MESSAGE_FILES_SYSTEM_PROPERTY, OTHER_MESSAGE_FILE);
		
		Locale locale = new Locale("en_US");
		MultipleBundlesSource source = new MultipleBundlesSource();
		String msg = source.getMessage(OTHER_MESSAGE_KEY, locale);
		
		assertEquals(OTHER_LOCALIZED_MESSAGE, msg);
		
		System.clearProperty(MESSAGE_FILES_SYSTEM_PROPERTY);
	}
	
	@Test
	public void testNoKeyText() {
		System.setProperty(MESSAGE_FILES_SYSTEM_PROPERTY, OTHER_MESSAGE_FILE);
		
		MultipleBundlesSource source = new MultipleBundlesSource();
		String msg = source.getMessage(NO_KEY_TEXT);
		
		assertEquals(NO_KEY_TEXT, msg);
		
		System.clearProperty(MESSAGE_FILES_SYSTEM_PROPERTY);
	}
	
	@Test
	public void testKeyNotMaped() {
		System.setProperty(MESSAGE_FILES_SYSTEM_PROPERTY, OTHER_MESSAGE_FILE);
		
		MultipleBundlesSource source = new MultipleBundlesSource();
		String msg = source.getMessage(KEY_NOT_MAPPED);
		
		assertEquals(KEY_NOT_MAPPED, msg);
		
		System.clearProperty(MESSAGE_FILES_SYSTEM_PROPERTY);
	}
	
}
