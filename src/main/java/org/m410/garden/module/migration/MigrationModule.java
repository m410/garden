package org.m410.garden.module.migration;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.application.ApplicationModule;
import org.m410.garden.application.annotate.Startup;

/**
 * A mock up of a module that implements the flywaydb database migration.
 *
 * @author Michael Fortin
 */
public interface MigrationModule extends ApplicationModule {

    @Startup
    default void initMigration(ImmutableHierarchicalConfiguration c) {
        // run migration
    }
}
