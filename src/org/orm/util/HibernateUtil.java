package org.orm.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	// Declare SessionFactory object and assign value to it.
	private static SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {

			// Create the SessionFactory from hibernate.cfg.xml located in
			// default path(src/hibernate.cfg.xml )
			return new Configuration().configure("/org/orm/util/hibernate.cfg.xml").buildSessionFactory();

		} catch (Throwable ex) {
			ex.printStackTrace();
			// Log the exception
			System.err.println("SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}

	public static SessionFactory getSessionFactory() throws HibernateException,
			Exception {
		return sessionFactory;
	}


	public static void shutdown() throws HibernateException, Exception {

		// Close caches and connection pools
		getSessionFactory().close();
	}

}