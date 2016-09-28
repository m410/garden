package org.m410.garden.module.jpa;

import com.google.common.collect.ImmutableList;
import org.m410.garden.application.ApplicationModule;
import org.m410.garden.application.annotate.ThreadLocalProvider;
import org.m410.garden.transaction.ThreadLocalSessionFactory;
import org.m410.garden.configuration.Configuration;

import java.util.List;

/**
 * Add jpa persistence to the application using Hibernate.
 *
 * @author Michael Fortin
 */
public interface JpaModule extends ApplicationModule {

    @ThreadLocalProvider
    static List<? extends ThreadLocalSessionFactory<?>> makeJpaThreadLocal(Configuration c) {
        return ImmutableList.of(
                new HibernatePersistence(c)
        );
    }

}
