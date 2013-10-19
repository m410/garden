package us.m410.j8.persistence;

import us.m410.j8.configuration.Configuration;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class EntityManagerFactoryFactory implements ThreadLocalFactory<JpaThreadLocal> {
    private Configuration configuration;

    public EntityManagerFactoryFactory(Configuration configuration) {
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
