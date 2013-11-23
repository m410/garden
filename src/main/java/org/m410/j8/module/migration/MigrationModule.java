package org.m410.j8.module.migration;

import org.m410.j8.application.ApplicationModule;
import org.m410.j8.configuration.Configuration;

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
