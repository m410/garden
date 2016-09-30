package org.m410.garden.zone;

/**
 * @author Michael Fortin
 */
public interface ZoneHandlerFactory {

    /**
     * applies an InvocationHandler to the instance of the class, and returns the java.reflect.Proxy
     * of the interface.  The InvocationHandler works with the Zone to access ThreadLocal variables
     * within this thread zone.
     *
     * @param interfce to proxy using java.reflect.Proxy.
     * @param instance the instance class of the interface to be called through the proxy.
     * @param <T> the type of class.
     * @return a java.reflect.Proxy instance of type T.
     */
    <T> T proxy(Class<T> interfce, T instance);

    // todo support multiple interfaces
}
