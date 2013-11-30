package org.m410.j8.module.jpa;

import org.m410.j8.application.ThreadLocalSessionFactory;
import org.m410.j8.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * A hibernate implementation of the threadLocalSession factory.  It's created by the
 * JpaModule and injected into the application to manage the persistence context.
 *
 * It's configuration comes from the configuration.m410.yml file which is also
 * response for the generation of the persistence.xml during the build process.
 *
 * @author Michael Fortin
 */
public class HibernatePersistence implements ThreadLocalSessionFactory<JpaThreadLocal> {
    private static final Logger log = LoggerFactory.getLogger(HibernatePersistence.class);

    private EntityManagerFactory entityManagerFactory;

    public HibernatePersistence(Configuration configuration) {
        entityManagerFactory = Persistence.createEntityManagerFactory("m410-jpa");
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
