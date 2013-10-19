package us.m410.j8.persistence;

import us.m410.j8.configuration.Configuration;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface MigrationComponent {
    default void doMigration(Configuration c) {

    }
}
