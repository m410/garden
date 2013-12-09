package org.m410.j8.application;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author m410
 */
public interface TypeTest<T> {
    default T transactional(T instance, Class<T>... cs ) {
        InvocationHandler handler = (proxy, method, args) -> method.invoke(instance,args);
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        return (T)Proxy.newProxyInstance(contextClassLoader,cs,handler);
    }
}
