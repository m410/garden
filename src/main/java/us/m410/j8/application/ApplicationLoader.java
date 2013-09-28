package us.m410.j8.application;

import us.m410.j8.configuration.Configuration;
import us.m410.j8.configuration.ConfigurationBuilder;

import java.lang.reflect.Constructor;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ApplicationLoader {

    public Application load()  {
        ClassLoader applicationLoader = getClass().getClassLoader();
//        LoggerFactory.getLogger(getClass).debug("app loader classloader={}",applicationLoader)

        try {
            Configuration config = ConfigurationBuilder.runtime("development");
            String projectApplicationClass = config.application().applicationClass();

            Class appClass = applicationLoader.loadClass(projectApplicationClass);
            Constructor constructor = appClass.getConstructor(Configuration.class);
            return (Application)constructor.newInstance(config);
        }
        catch(Throwable e) {
//          log.error("Could not load application because: " + e.getMessage, e)
            throw new LoadFailureException(e);
            // will become a reflection error above, but logging here anyway to get better
            // details in the stack trace, it gets truncated above.
        }
    }
}
