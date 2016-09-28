package org.m410.garden.module.jms;

import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.application.ApplicationModule;
import org.m410.garden.application.annotate.ComponentsProvider;

import java.util.List;

/**
 * Add JMS functionality to the application.  This is a non-working
 * mock up.
 *
 * @author Michael Fortin
 */
public interface JmsModule extends ApplicationModule {

    @ComponentsProvider
    default List<?> makeJmsServices(ImmutableHierarchicalConfiguration c) {
        return ImmutableList.of();
    }
}
