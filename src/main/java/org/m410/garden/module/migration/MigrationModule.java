package org.m410.garden.module.migration;

import org.m410.garden.application.ApplicationModule;
import org.m410.garden.configuration.Configuration;

/**
 * A mock up of a module that implements the flywaydb database migration.
 *
 * @author Michael Fortin
 */
public interface MigrationModule extends ApplicationModule {
    default void init(Configuration c) {
        // run migration
    }
}
