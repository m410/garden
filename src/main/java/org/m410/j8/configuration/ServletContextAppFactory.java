package org.m410.j8.configuration;

import org.m410.j8.application.ApplicationModule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Creates a configuration at runtime using the servlet context.
 *
 * @author Michael Fortin
 */
public class ServletContextAppFactory {
    public static ApplicationModule forEnvironment(String env) {
        Configuration config = ConfigurationFactory.runtime(env);

        try {
            Class clazz = Class.forName(config.getApplication().getApplicationClass());
            Constructor constructor = clazz.getConstructor(Configuration.class);
            return (ApplicationModule) constructor.newInstance(config);
        }
        catch (ClassNotFoundException|InstantiationException|InvocationTargetException|
                IllegalAccessException|NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
