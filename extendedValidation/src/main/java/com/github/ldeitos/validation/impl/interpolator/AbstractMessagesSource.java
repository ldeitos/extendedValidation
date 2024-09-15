package com.github.ldeitos.validation.impl.interpolator;

import static com.github.ldeitos.constants.Constants.MESSAGE_KEY_PATTERN;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.ldeitos.constants.Constants;
import com.github.ldeitos.validation.MessagesSource;

/**
 * Abstract implementation to {@link MessagesSource}.<br>
 * Provide a {@link Pattern} compiled by {@link Constants#MESSAGE_KEY_PATTERN}, a key group index in regex pattern, 
 * a {@link Matcher} provider method and default implementation to {@link MessagesSource#getMessage(String)} 
 * and {@link MessagesSource#getMessage(String, Locale)}
 * 
 * @author <a href="mailto:leandro.deitos@gmail.com">Leandro Deitos</a>
 *
 */
public abstract class AbstractMessagesSource implements MessagesSource {
	
	/**
	 * Pattern compiled by {@link Constants#MESSAGE_KEY_PATTERN} pattern.
	 */
	protected static final Pattern PATTERN_BUNDLE_KEY = Pattern.compile(MESSAGE_KEY_PATTERN);
	
	/**
	 * Key content group index in regex key pattern.
	 */
	protected static int BUNDLE_KEY_GROUP = 2;

	/**
	 * {@inheritDoc}
	 */
	public String getMessage(String key) {
		return getMessage(key, null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getMessage(String key, Locale locale) {
		String msg = new String(key);		
		Matcher matcher = getMatcher(key);
		
		if(matcher.matches()){
			msg = getInSource(msg, matcher.group(BUNDLE_KEY_GROUP), locale);
		}
		
		return msg;
	}
	
	/**
	 * @param msg
	 * 		String to be matched by {@link #PATTERN_BUNDLE_KEY}.
	 * @return
	 * 		{@link Matcher} instance build by {@link #PATTERN_BUNDLE_KEY} pattern.
	 */
	protected Matcher getMatcher(String msg) {
		return PATTERN_BUNDLE_KEY.matcher(msg);
	}

	/**
	 * 
	 * @param original 
	 * 		Original 'message' constraint attribute content.<br>
	 *      E.g: "{keyContent}"
	 * @param key
	 * 		Key extracted from original message if that is key pattern compatible.<br>
	 *      E.g: "{keyContent}" or "[keyContent]" result "keyContent"
	 * @param locale 
	 * 		Locale to get localized message, may be <code>null</code>.
	 * 
	 * @return
	 * 		The message obtained from message source by the key, or original 'message' 
	 * 	    content if the key does not gives any correspondent.
	 */
	protected abstract String getInSource(String original, String key, Locale locale);

}
