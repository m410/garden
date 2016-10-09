package org.m410.garden.module.jpa;

import com.google.common.collect.ImmutableList;
import org.m410.garden.application.annotate.ZoneProvider;
import org.m410.garden.zone.ZoneFactorySupplier;

/**
 * Add jpa persistence to the application using Hibernate.
 *
 * @author Michael Fortin
 */
public interface JpaModule {

    @ZoneProvider
    default ZoneFactorySupplier makeJpaZone() {
        System.out.println("makeJpaThreadLocal, hibernateFactory");
        return configuration -> ImmutableList.of(new HibernateZoneFactory(configuration));
    }
}
