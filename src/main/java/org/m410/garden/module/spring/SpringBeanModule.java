package org.m410.garden.module.spring;

import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.application.ApplicationModule;
import org.m410.garden.application.annotate.ComponentsProvider;

import java.util.List;

/**
 * This is not implemented.  The concept is that if you would prefer
 * to use spring bean annotations to wire your class instead of
 * explicit wiring you you would have that option simply by adding
 * this module to your application.
 *
 * todo not implemented
 *
 * @author Michael Fortin
 */
public interface SpringBeanModule extends ApplicationModule {

    @ComponentsProvider
    default List<?> makeSpringServices(ImmutableHierarchicalConfiguration c) {
        return ImmutableList.of();
    }
}
