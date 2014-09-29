package extended.util;

import java.lang.annotation.Annotation;
import java.util.Iterator;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
 
/**
 * Provides simple way how to obtain the instance of managed beans
 * in non-managed environment (POJOs, etc).
 *
 * @author Vaclav Svejcar (v.svejcar@norcane.cz)
 */
public class ManualContext {
 
	private static final Context ctx;
	private static final BeanManager beanManager;
 
	static {
		try {
			ctx = new InitialContext();
 
			// manual JNDI lookupCDI for the CDI bean manager (JSR 299)
			beanManager = (BeanManager) ctx.lookup(
					"java:comp/BeanManager");
		} catch (NamingException ex) {
			throw new IllegalStateException(
					"cannot perform JNDI lookup for CDI BeanManager");
		}
	}
 
	/**
	 * Returns the instance of the CDI managed bean, specified by its class type
	 * and optionally by annotations/qualifiers.
	 *
	 * @param <T> class type of the desired CDI managed bean
	 * @param theClass class of the desired CDI manager bean
	 * @param annotations CDI manager bean annotations/qualifiers
	 * @return instance of the CDI managed bean
	 */
	public static <T> T lookupCDI(Class<T> theClass, Annotation... annotations) {
		T beanReference = null;
		Iterator<Bean<?>> it = beanManager.getBeans(theClass, annotations).iterator();

		if(it.hasNext()){
			Bean<T> bean = (Bean<T>) it.next();
			CreationalContext<T> cCtx = beanManager.createCreationalContext(bean);
			beanReference = (T) beanManager.getReference(bean, theClass, cCtx);
		}
 
		return beanReference;
	}
}