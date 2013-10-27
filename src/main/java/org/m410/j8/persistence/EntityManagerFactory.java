package org.m410.j8.persistence;

import org.m410.j8.configuration.Configuration;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class EntityManagerFactory implements ThreadLocalFactory<JpaThreadLocal> {
    private Configuration configuration;

    public EntityManagerFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public JpaThreadLocal make() {
        return null;
    }

    @Override
    public void shutdown() {
        // todo deregister dataSource
    }
}
