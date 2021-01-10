package org.m410.garden.module.jpa;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.zone.ZoneFactory;
import org.m410.garden.zone.ZoneHandlerFactory;
import org.m410.garden.zone.ZoneManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;

import static org.m410.garden.module.jpa.JpaZone.JPA_ZONE;

/**
 * A hibernate implementation of the threadLocalSession factory.  It's created by the
 * JpaModule and injected into the application to manage the persistence context.
  * <p>
 * It's configuration comes from the garden.fab.yml file which is also
 * response for the generation of the persistence.xml during the build process.
 *
 * @author Michael Fortin
 */
public final class HibernateZoneFactory implements ZoneFactory<JpaZone> {
    private static final Logger log = LoggerFactory.getLogger(HibernateZoneFactory.class);

    private final String unitName;
    private final EntityManagerFactory entityManagerFactory;
    private ZoneManager zoneManager;

    public HibernateZoneFactory(ImmutableHierarchicalConfiguration configuration) {
        log.debug("thread classloader: {}" , Thread.currentThread().getContextClassLoader());
        log.debug("thread classloader res: {}" , Thread.currentThread().getContextClassLoader().getResource("META-INF/persistence.xml"));
        log.debug("class classloader: {}" , this.getClass().getClassLoader());
        log.debug("class classloader res: {}" , this.getClass().getClassLoader().getResource("META-INF/persistence.xml"));

        // nested propertyName
        unitName = configuration.getString("unit_name");

        // todo set other properties

        // todo replace with org.hibernate.jpa.HibernatePersistenceProvider
        final org.hibernate.ejb.HibernatePersistence persistence = new org.hibernate.ejb.HibernatePersistence();
        entityManagerFactory = persistence.createEntityManagerFactory(unitName, new HashMap());

        log.info("Created EntityManagerFactory: {}", entityManagerFactory);
    }


    @Override
    public void setZoneManager(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }

    @Override
    public String name() {
        return JPA_ZONE;
    }

    @Override
    public ZoneHandlerFactory zoneHandlerFactory() {
        return new HibernateZoneHandlerFactory(zoneManager);
    }

    /**
     * create a thread local with the entity manager factory.
     *
     * @return a thread local to manage connections.
     */
    @Override
    public JpaZone makeZone() {
        return new JpaZone(entityManagerFactory);
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
