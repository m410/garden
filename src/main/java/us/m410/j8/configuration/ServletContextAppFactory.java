package us.m410.j8.configuration;

import us.m410.j8.application.Application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ServletContextAppFactory {
    public static Application forEnvironment(String env) {
        Configuration config = ConfigurationFactory.runtime(env);

        try {
            Class clazz = Class.forName(config.getApplication().getApplicationClass());
            Constructor constructor = clazz.getConstructor(Configuration.class);
            return (Application)constructor.newInstance(config);
        }
        catch (ClassNotFoundException|NoSuchMethodException|InstantiationException|
                IllegalAccessException|InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
