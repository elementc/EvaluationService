package org.orm.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	// Declare SessionFactory object and assign value to it.
	private static SessionFactory sessionFactory;

	private static SessionFactory buildSessionFactory() {
		try {

			// Create the SessionFactory from hibernate.cfg.xml located in
			// default path(src/hibernate.cfg.xml )
            return new AnnotationConfiguration().configure("/org/orm/util/hibernate.cfg.xml").buildSessionFactory();

		} catch (HibernateException e) {
			e.printStackTrace();
			// Log the exception
			System.err.println("SessionFactory creation failed." + e);
			throw new HibernateException(e);
		} catch (Exception e) {
			e.printStackTrace();
			// Log the exception
			System.err.println("SessionFactory creation failed." + e);
			throw new HibernateException(e);
		} catch (Throwable ex) {
			ex.printStackTrace();
			// Log the exception
			System.err.println("SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}

	public static SessionFactory getSessionFactory() throws HibernateException,
			Exception {
        if(sessionFactory == null || sessionFactory.isClosed()){
            sessionFactory = buildSessionFactory();
        }
		return sessionFactory;
	}


	public static void shutdown() throws HibernateException, Exception {

		// Close caches and connection pools
		getSessionFactory().close();
	}

}