package org.m410.garden.module.jpa;

import org.m410.garden.transaction.ThreadLocalSessionFactory;
import org.m410.garden.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * A hibernate implementation of the threadLocalSession factory.  It's created by the
 * JpaModule and injected into the application to manage the persistence context.
  * <p>
 * It's configuration comes from the garden.fab.yml file which is also
 * response for the generation of the persistence.xml during the build process.
 *
 * @author Michael Fortin
 */
public class HibernatePersistence implements ThreadLocalSessionFactory<JpaThreadLocal> {
    private static final Logger log = LoggerFactory.getLogger(HibernatePersistence.class);

    private EntityManagerFactory entityManagerFactory;

    public HibernatePersistence(Configuration configuration) {
        log.warn("thread classloader: {}" , Thread.currentThread().getContextClassLoader());
        log.warn("thread classloader res: {}" , Thread.currentThread().getContextClassLoader().getResource("META-INF/persistence.xml"));
        log.warn("class classloader: {}" , this.getClass().getClassLoader());
        log.warn("class classloader res: {}" , this.getClass().getClassLoader().getResource("META-INF/persistence.xml"));

        // todo get entityManager name from configuration

        final org.hibernate.ejb.HibernatePersistence persistence = new org.hibernate.ejb.HibernatePersistence();
        entityManagerFactory = persistence.createEntityManagerFactory("m410-jpa", new HashMap());
        log.info("Created EntityManagerFactory: {}", entityManagerFactory);
    }

    /**
     * create a thread local with the entity manager factory.
     *
     * @return a thread local to manage connections.
     */
    @Override
    public JpaThreadLocal make() {
        return new JpaThreadLocal(entityManagerFactory);
    }

    /**
     * Shutdown and connection pools
     */
    @Override
    public void shutdown() {
        log.debug("shutting down and de-registering jdbc drivers");
        entityManagerFactory.close();
        Enumeration<Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            log.debug("de-register jdbc driver: {}",driver);

            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
