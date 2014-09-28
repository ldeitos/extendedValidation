package extended.validation;

import java.util.Set;

/**
 * 
 * @author <a href=mailto:leandro.deitos@gmail.com>Leandro Deitos</a>
 *
 */
public interface Validator extends javax.validation.Validator {
	public <T> Set<Message> validateBean(T object,
		Class<?>... groups);

	public <T> Set<Message> validatePropertyBean(T object,
			String propertyName, Class<?>... groups);

	public <T> Set<Message> validateValueBean(Class<T> beanType,
		String propertyName, Object value, Class<?>... groups);

}
