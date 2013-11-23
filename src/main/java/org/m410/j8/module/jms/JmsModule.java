package org.m410.j8.module.jms;

import com.google.common.collect.ImmutableList;
import org.m410.j8.application.ApplicationModule;
import org.m410.j8.configuration.Configuration;

import java.util.List;

/**
 * Add JMS functionality to the application.  This is a non-working
 * mock up.
 *
 * @author Michael Fortin
 */
public interface JmsModule extends ApplicationModule {
    default List<?> makeServices(Configuration c) {
        return ImmutableList.of();
    }
}
