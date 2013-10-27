package org.m410.j8.configuration;

import org.m410.j8.application.ApplicationComponent;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ServletContextAppFactory {
    public static ApplicationComponent forEnvironment(String env) {
        Configuration config = ConfigurationFactory.runtime(env);

        try {
            Class clazz = Class.forName(config.getApplication().getApplicationClass());
            final ApplicationComponent application = (ApplicationComponent) clazz.newInstance();
            application.init(config);
            return application;
        }
        catch (ClassNotFoundException|InstantiationException|
                IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
