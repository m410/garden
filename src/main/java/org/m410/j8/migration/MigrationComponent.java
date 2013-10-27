package org.m410.j8.migration;

import org.m410.j8.application.ApplicationComponent;
import org.m410.j8.configuration.Configuration;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface MigrationComponent extends ApplicationComponent {
    default void init(Configuration c) {
        // run migration
    }
}
