package org.m410.j8.module.jpa;

import com.google.common.collect.ImmutableList;
import org.m410.j8.application.ApplicationModule;
import org.m410.j8.application.ThreadLocalSessionFactory;
import org.m410.j8.configuration.Configuration;

import java.util.List;

/**
 * Add jpa persistence to the application using Hibernate.
 *
 * @author Michael Fortin
 */
public interface JpaModule extends ApplicationModule {

    default List<ThreadLocalSessionFactory> makeThreadLocalFactories(Configuration c) {
        return ImmutableList.of(
                new HibernatePersistence(c)
        );
    }
}
