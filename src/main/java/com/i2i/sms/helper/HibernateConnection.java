
package com.i2i.sms.helper;

import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * This class that provides a Hibernate SessionFactory instance.
 * This class manages the creation and retrieval of the SessionFactory.
 */
public class HibernateConnection{

    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    //It creates the sessionFactory instance
    private static SessionFactory buildSessionFactory() {
        try {
            Dotenv dotenv = Dotenv.configure().directory("./src/main/resources").load();
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.driver_class", dotenv.get("DB_DRIVER"));
            configuration.setProperty("hibernate.connection.url", dotenv.get("DB_URL"));
            configuration.setProperty("hibernate.connection.username", dotenv.get("DB_USERNAME"));
            configuration.setProperty("hibernate.connection.password", dotenv.get("DB_PASSWORD"));
            configuration.configure("hibernate.cfg.xml");
            return configuration.buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    //It returns the sessionFactory instance 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
}
