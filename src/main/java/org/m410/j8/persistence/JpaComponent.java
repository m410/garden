package org.m410.j8.persistence;

import com.google.common.collect.ImmutableList;
import org.m410.j8.application.ApplicationComponent;
import org.m410.j8.configuration.Configuration;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface JpaComponent extends ApplicationComponent {

    default List<? extends ThreadLocalSessionFactory> makeThreadLocalFactories(Configuration c) {
        return ImmutableList.of(
                new HibernatePersistence(c)
        );
    }
}
