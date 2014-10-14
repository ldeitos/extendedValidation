package com.github.ldeitos.util;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

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
	
	private static final String BEAN_MANAGER_JNDI = "java:comp/BeanManager";
    private static final String BEAN_MANAGER_JNDI_FALLBACK = "java:comp/env/BeanManager";

 
	private static BeanManager getBeanManager() {
		try {
			Context ctx = new InitialContext();
 
			// manual JNDI lookupCDI for the CDI bean manager (JSR 299)
			BeanManager beanManager = (BeanManager) ctx.lookup(BEAN_MANAGER_JNDI);
			
			if(beanManager == null) {
				beanManager = (BeanManager) ctx.lookup(BEAN_MANAGER_JNDI_FALLBACK);
			}
			
			return beanManager;
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
		BeanManager beanManager = getBeanManager();
		Set<Bean<?>> beans = beanManager.getBeans(theClass, annotations);
		Iterator<Bean<?>> it = beans.iterator();

		if(it.hasNext()){
			Bean<T> bean = (Bean<T>) beanManager.resolve(beans);
			CreationalContext<T> cCtx = beanManager.createCreationalContext(bean);
			beanReference = (T) beanManager.getReference(bean, theClass, cCtx);
		}
 
		return beanReference;
	}
}