package org.m410.garden.module.jpa;

import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.application.ApplicationModule;
import org.m410.garden.application.annotate.ZoneProvider;
import org.m410.garden.zone.ZoneFactorySupplier;

/**
 * Add jpa persistence to the application using Hibernate.
 *
 * @author Michael Fortin
 */
public interface JpaModule extends ApplicationModule {

    @ZoneProvider
    static ZoneFactorySupplier makeJpaThreadLocal(final ImmutableHierarchicalConfiguration c) {
        return configuration -> ImmutableList.of(new HibernateZoneFactory(c));
    }
}
