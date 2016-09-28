package org.m410.garden.di.app;

import org.m410.garden.di.InvocationHandlerFactory;

import java.lang.reflect.*;

/**
 * @author Michael Fortin
 */
public class LogInvocationHandlerFactory implements InvocationHandlerFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T proxy(Class<T> interfce, T instance) {
        Class proxyClass = Proxy.getProxyClass(interfce.getClassLoader(), interfce );

        try {
            final Constructor constructor = proxyClass.getConstructor(InvocationHandler.class);
            return (T) constructor.newInstance(new Handler(instance));
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private class Handler implements InvocationHandler {
        Object target;

        Handler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("log "+target.getClass().getSimpleName() +":"+ method.getName());
            return method.invoke(target, args);
        }
    }
}
