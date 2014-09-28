package extended.validation;

import java.util.Locale;

/**
 * 
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public interface MessagesSource {
	String getMessage(String key);
	
	String getMessage(String key, Locale locale);
}
