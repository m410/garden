package org.m410.garden.application;

import org.m410.garden.configuration.Configuration;
import org.m410.garden.configuration.ConfigurationFactory;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * This loads the application but with a small caveat.  The application must be loaded
 * by the child classloader in development mode.  So the application needs to override this
 * class.
  * <p>
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
    @SuppressWarnings("unchecked")
    public Application load(String env) {
        ClassLoader appClassLoader = getClass().getClassLoader();

        try {
            Configuration config = ConfigurationFactory.runtime(env);
            String projectApplicationClass = config.getApplication().getApplicationClass();

            Class appClass = appClassLoader.loadClass(projectApplicationClass);
            Object instance = appClass.newInstance();
            Method initMethod = appClass.getMethod("init",Configuration.class);
            initMethod.invoke(instance, config);
            return (Application) instance;
        }
        catch (Throwable e) {
            System.out.println("---- ApplicationLoader error: " + e.getMessage());
            e.printStackTrace();
            dumpThreadClasspath();
            System.out.println("---- ApplicationLoader error");
//          log.error("Could not load application because: " + e.getMessage, e)
            throw new LoadFailureException(e);
            // will become a reflection error above, but logging here anyway to get better
            // details in the stack trace, it gets truncated above.
        }
    }

    public void dumpThreadClasspath() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        do {
            System.out.println("classloader: " + classloader );
            URL[] urls = ((URLClassLoader)classloader).getURLs();

            for(URL url: urls)
                System.out.println("    " + url.getFile());

            classloader = (URLClassLoader)classloader.getParent();
        } while(classloader != null);
    }
}
