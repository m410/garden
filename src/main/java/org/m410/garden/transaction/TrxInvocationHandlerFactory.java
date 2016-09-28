package org.m410.garden.transaction;

import org.m410.garden.di.InvocationHandlerFactory;

import java.lang.reflect.Proxy;

/**
 * @author Michael Fortin
 */
public class TrxInvocationHandlerFactory implements InvocationHandlerFactory {
    @Override
    public <T> T proxy(Class<T> interfce, T instance) {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final Class[] interfaces = {interfce};
        final TransactionHandler<T> handler = new TransactionHandler<T>(instance);
        return (T) Proxy.newProxyInstance(loader, interfaces, handler);
    }
}
