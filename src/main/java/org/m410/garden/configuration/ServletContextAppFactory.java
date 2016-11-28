package org.m410.garden.configuration;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.application.ApplicationModule;

/**
 * Creates a configuration at runtime using the servlet context.
 *
 * @author Michael Fortin
 */
public final class ServletContextAppFactory {
    public static ApplicationModule forEnvironment(String env) {
        ImmutableHierarchicalConfiguration config = ConfigurationFactory.runtime(env);

        try {
            Class clazz = Class.forName(config.getString("application.applicationClass"));
            final ApplicationModule application = (ApplicationModule) clazz.newInstance();
            application.init(config);
            return application;
        }
        catch (ClassNotFoundException|InstantiationException|
                IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
