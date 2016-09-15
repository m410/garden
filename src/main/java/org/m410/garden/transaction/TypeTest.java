package org.m410.garden.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author m410
 */
public interface TypeTest<T> {

    @SuppressWarnings("unchecked")
    default T transactional(T instance, Class<T>... cs ) {
        InvocationHandler handler = (proxy, method, args) -> method.invoke(instance,args);
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        return (T)Proxy.newProxyInstance(contextClassLoader,cs,handler);
    }
}
