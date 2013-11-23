package org.m410.j8.module.mail;

import com.google.common.collect.ImmutableList;
import org.m410.j8.application.ApplicationModule;
import org.m410.j8.configuration.Configuration;

import java.util.List;

/**
 * Add Mail sending a ability to an application.
 *
 * @author Michael Fortin
 */
public interface MailModule extends ApplicationModule {
    default List<?> makeServices(Configuration c) {
        return ImmutableList.of(new MailService());
    }
}
