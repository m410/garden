package org.m410.j8.module.spring;

import com.google.common.collect.ImmutableList;
import org.m410.j8.application.ApplicationModule;
import org.m410.j8.configuration.Configuration;

import java.util.List;

/**
 * This is not implemented.  The concept is that if you would prefer
 * to use spring bean annotations to wire your class instead of
 * explicit wiring you could add this module to your application.
 *
 * @todo not implemented
 * @author Michael Fortin
 */
public interface SpringBeanModule extends ApplicationModule {
    default List<?> makeServices(Configuration c) {
        return ImmutableList.of();
    }
}
