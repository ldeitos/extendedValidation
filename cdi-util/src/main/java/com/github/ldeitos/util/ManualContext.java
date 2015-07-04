package com.github.ldeitos.util;

import static java.lang.String.format;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ldeitos.exception.InvalidCDIContextException;

/**
 * Provides simple way how to obtain the instance of managed beans in
 * non-managed environment (POJOs, etc).
 *
 */
public class ManualContext implements Extension {

	/**
	 * JNDI name to lookup a CDI BeanManager
	 */
	private static final String BEAN_MANAGER_JNDI = "java:comp/BeanManager";

	/**
	 * The CDI BeanManager
	 */
	private static BeanManager beanManager;

	private static Logger log = LoggerFactory.getLogger(ManualContext.class);

	public static void startup(@Observes final BeforeBeanDiscovery event, BeanManager beanManager) {
		ManualContext.beanManager = beanManager;
		logBeanManagerObtainedByEventObserver();
	}

	/**
	 * Returns the instance of the CDI managed bean obtained by BeanManager
	 * lookup.
	 *
	 * @param <T>
	 *            class type of the desired CDI managed bean
	 * @param theClass
	 *            class of the desired CDI manager bean
	 * @param annotations
	 *            CDI manager bean annotations/qualifiers
	 * @return instance of the CDI managed bean
	 */
	public static <T> T lookupCDI(Class<T> theClass, Annotation... annotations) {
		return lookupByBeanManager(theClass, annotations);
	}

	/**
	 * Returns the instance of the CDI managed bean obtained by BeanManager
	 * lookup.
	 *
	 * @param <T>
	 *            class type of the desired CDI managed bean
	 * @param theClass
	 *            class of the desired CDI manager bean
	 * @param annotations
	 *            CDI manager bean annotations/qualifiers
	 * @return instance of the CDI managed bean
	 *
	 * @since 0.6.2
	 */
	public static <T> T lookupCDI(BeanManager bm, Class<T> theClass, Annotation... annotations) {
		beanManager = bm;
		return lookupByBeanManager(theClass, annotations);
	}

	/**
	 * Returns the instance of the CDI managed bean, specified by its class type
	 * and optionally by annotations/qualifiers.
	 *
	 * @param <T>
	 *            class type of the desired CDI managed bean
	 * @param theClass
	 *            class of the desired CDI manager bean
	 * @param annotations
	 *            CDI manager bean annotations/qualifiers
	 * @return instance of the CDI managed bean
	 */
	private static <T> T lookupByBeanManager(Class<T> theClass, Annotation... annotations) {
		assertHasBeanManager();
		T beanReference = getBeanReferenceTo(theClass, annotations);

		if (beanReference == null) {
			log.warn(format("Impossible retrieve bean reference to [%s].", theClass));
		}

		return beanReference;
	}

	@SuppressWarnings("unchecked")
	private static <T> T getBeanReferenceTo(Class<T> theClass, Annotation... annotations) {
		T beanReference = null;
		Set<Bean<?>> beans = beanManager.getBeans(theClass, annotations);
		Iterator<Bean<?>> it = beans.iterator();

		if (it.hasNext()) {
			Bean<T> bean = (Bean<T>) beanManager.resolve(beans);
			CreationalContext<T> cCtx = beanManager.createCreationalContext(bean);
			beanReference = (T) beanManager.getReference(bean, theClass, cCtx);
			log.debug(format("Bean reference to [%s] successfuly retrieved.", theClass));
		}

		return beanReference;
	}

	private static void assertHasBeanManager() {
		if (beanManager == null) {
			try {
				lookupBeanManagerByJNDI();
			} catch (NamingException e) {
				String errorMsg = format("Cannot perform JNDI lookup for CDI BeanManager: [%s]",
				    e.getMessage());
				log.error(errorMsg, e);
				InvalidCDIContextException.throwNew(errorMsg, e);
			} catch (ClassCastException e) {
				String errorMsg = format("Cannot perform cast from obtained object in JNDI lookup "
				    + "to CDI BeanManager: [%s]", e);
				log.error(errorMsg, e);
				InvalidCDIContextException.throwNew(errorMsg, e);
			}

			if (beanManager == null) {
				InvalidCDIContextException
				.throwNew("Invalid BeanManager state. Cannot locate by container initilization even JDNI loockup.");
			}
		}

		log.debug("BeanManager disponible to CDI lookup.");
	}

	/**
	 * @return BeanManager instance obtained by JNDI lookup.
	 * @throws NamingException
	 */
	private static void lookupBeanManagerByJNDI() throws NamingException {
		// manual JNDI lookupCDI for the CDI bean manager (JSR 299)
		Context ctx = new InitialContext();
		beanManager = (BeanManager) ctx.lookup(BEAN_MANAGER_JNDI);

		logBeanManagerObtainedByJNDILookup();
	}

	private static void logBeanManagerObtainedByJNDILookup() {
		String sucMsg = format("BeanManager retrieved by JNDI lookup from [%s] name.", BEAN_MANAGER_JNDI);
		String errMsg = format("Error to retrieve BeanManager by JNDI lookup from [%s] name.",
		    BEAN_MANAGER_JNDI);
		logDebug(beanManager != null, sucMsg, errMsg);
	}

	private static void logBeanManagerObtainedByEventObserver() {
		String sucMsg = format("BeanManager[%s] obtained by BeforeBeanDiscovery event observer", beanManager);
		String errMsg = "Isn't possible retrieve BeanManaber by BeforeBeanDiscovery event oberserver";
		logDebug(beanManager != null, sucMsg, errMsg);
	}

	private static void logDebug(boolean sucess, String sucMsg, String errMsg) {
		if (log.isDebugEnabled()) {
			log.debug(sucess ? sucMsg : errMsg);
		}
	}
}