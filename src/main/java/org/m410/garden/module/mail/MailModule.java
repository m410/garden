package org.m410.garden.module.mail;

import com.google.common.collect.ImmutableList;
import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.application.ApplicationModule;
import org.m410.garden.application.annotate.ComponentsProvider;

import java.util.List;

/**
 * Add Mail sending a ability to an application.
 *
 * @author Michael Fortin
 */
public interface MailModule extends ApplicationModule {

    @ComponentsProvider
    default List<?> makeMailService(ImmutableHierarchicalConfiguration c) {
        return ImmutableList.of(new MailService());
    }
}
