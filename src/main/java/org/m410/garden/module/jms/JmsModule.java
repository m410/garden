package org.m410.garden.module.jms;

import com.google.common.collect.ImmutableList;
import org.m410.garden.application.ApplicationModule;
import org.m410.garden.configuration.Configuration;

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
