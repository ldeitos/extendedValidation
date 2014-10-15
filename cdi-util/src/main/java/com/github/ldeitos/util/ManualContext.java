package com.github.ldeitos.util;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
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

	/**
	 * JNDI name to lookup.
	 */
	private static final String BEAN_MANAGER_JNDI = "java:comp/BeanManager";
	
	/**
	 * Returns the instance of the CDI managed bean obtained by BeanManager lookup or, if isn't possible obtain, 
	 * by {@link CDI} static class selection.
	 *
	 * @param <T> class type of the desired CDI managed bean
	 * @param theClass class of the desired CDI manager bean
	 * @param annotations CDI manager bean annotations/qualifiers
	 * @return instance of the CDI managed bean
	 */
	public static <T> T lookupCDI(Class<T> theClass, Annotation... annotations) {
		T beanReference = lookupByBeanManager(theClass, annotations);
		
		if(beanReference == null) {
			beanReference = CDI.current().select(theClass, annotations).get();
		}
 
		return beanReference;
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
	private static <T> T lookupByBeanManager(Class<T> theClass, Annotation... annotations) {
		T beanReference = null;
		BeanManager beanManager = getBeanManager();
		Set<Bean<?>> beans = beanManager.getBeans(theClass, annotations);
		Iterator<Bean<?>> it = beans.iterator();
		
		CDI.current().select(theClass, annotations).get();

		if(it.hasNext()){
			Bean<T> bean = (Bean<T>) beanManager.resolve(beans);
			CreationalContext<T> cCtx = beanManager.createCreationalContext(bean);
			beanReference = (T) beanManager.getReference(bean, theClass, cCtx);
		}
		
		return beanReference;
	}

	/**
	 * @return BeanManager instance obtained by JNDI lookup or, case fail by that, 
	 * by {@link CDI} static access. 
	 */
	private static BeanManager getBeanManager() {		
		BeanManager beanManager = getBeanManagerByJNDI();			

		if(beanManager == null){
			beanManager = CDI.current().getBeanManager();
		}

		return beanManager;			
	}

	/**
	 * @return BeanManager instance obtained by JNDI lookup.
	 */
	private static BeanManager getBeanManagerByJNDI() {
		try {
			// manual JNDI lookupCDI for the CDI bean manager (JSR 299)
			Context ctx = new InitialContext();
			return (BeanManager) ctx.lookup(BEAN_MANAGER_JNDI);
		} catch (NamingException ex) {
			throw new IllegalStateException(
					"cannot perform JNDI lookup for CDI BeanManager");
		}	
	}
}