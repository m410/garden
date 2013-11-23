package org.m410.j8.configuration;

import org.m410.j8.application.ApplicationModule;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ServletContextAppFactory {
    public static ApplicationModule forEnvironment(String env) {
        Configuration config = ConfigurationFactory.runtime(env);

        try {
            Class clazz = Class.forName(config.getApplication().getApplicationClass());
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
