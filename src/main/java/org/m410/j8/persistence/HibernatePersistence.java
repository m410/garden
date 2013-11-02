package org.m410.j8.persistence;

import org.m410.j8.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class HibernatePersistence implements ThreadLocalSessionFactory<JpaThreadLocal> {
    private static final Logger log = LoggerFactory.getLogger(HibernatePersistence.class);

    private Configuration configuration;
    private EntityManagerFactory entityManagerFactory;

    public HibernatePersistence(Configuration configuration) {
        this.configuration = configuration;
        entityManagerFactory = Persistence.createEntityManagerFactory("m410-jpa");
        log.info("Created EntityManagerFactory: {}", entityManagerFactory);
    }

    @Override
    public JpaThreadLocal make() {
        return new JpaThreadLocal(entityManagerFactory);
    }

    @Override
    public void shutdown() {
        // todo deregister driver
    }
}
