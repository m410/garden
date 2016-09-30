package org.m410.garden.module.jpa;

import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.application.ApplicationModule;
import org.m410.garden.application.annotate.ThreadLocalProvider;
import org.m410.garden.zone.ZoneFactory;

import java.util.List;

/**
 * Add jpa persistence to the application using Hibernate.
 *
 * @author Michael Fortin
 */
public interface JpaModule extends ApplicationModule {

    @ThreadLocalProvider
    static List<? extends ZoneFactory<?>> makeJpaThreadLocal(ImmutableHierarchicalConfiguration c) {
        return ImmutableList.of(
                new HibernatePersistence(c)
        );
    }

}
