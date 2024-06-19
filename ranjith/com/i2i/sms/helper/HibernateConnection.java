
package com.i2i.sms.helper;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

/**
 * This class that provides a Hibernate SessionFactory instance.
 * This class manages the creation and retrieval of the SessionFactory.
 */
public class HibernateConnection{

    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    //It creates the sessionFactory instance
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    //It returns the sessionFactory instance 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
}
