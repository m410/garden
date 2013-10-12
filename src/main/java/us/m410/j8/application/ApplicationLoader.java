package us.m410.j8.application;

import us.m410.j8.configuration.Configuration;
import us.m410.j8.configuration.ConfigurationFactory;

import java.lang.reflect.Constructor;

/**
 * This loads the application but with a small caveat.  The application must be loaded
 * by the child classloader in development mode.  So the application needs to override this
 * class.
 *
 * For the server to be able to find the class, it must adherer to a specific naming convention.  If
 * you application class is com.mycompany.webapp.MyCompanyApplication, then the loader must be
 * named com.mycompany.webapp.MyCompanyApplicationLoader.
 *
 * @author Michael Fortin
 */
public class ApplicationLoader {

    /**
     * Creates a new instance of the application class in a new classloader.
     * @return an instance of the Application class.
     */
    public Application load() {
        ClassLoader appClassLoader = getClass().getClassLoader();

        try {
            Configuration config = ConfigurationFactory.runtime("development");
            String projectApplicationClass = config.getApplication().getApplicationClass();

            Class appClass = appClassLoader.loadClass(projectApplicationClass);
            Constructor constructor = appClass.getConstructor(Configuration.class);
            return (Application) constructor.newInstance(config);
        }
        catch (Throwable e) {
//          log.error("Could not load application because: " + e.getMessage, e)
            throw new LoadFailureException(e);
            // will become a reflection error above, but logging here anyway to get better
            // details in the stack trace, it gets truncated above.
        }
    }
}
