package org.m410.garden.transaction;

import org.m410.garden.application.Application;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Wraps each call on a service method with a transaction.
 *
 * @author Michael Fortin
 */
public final class TransactionHandler<T> implements InvocationHandler {

    private final T instance;
    private final List<String> methodFilter;
    private final Application application ;

    public TransactionHandler(T instance, String[] methodFilter, Application application) {
        this.instance = instance;
        this.methodFilter = Arrays.asList(methodFilter);
        this.application = application;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(methodIsTransactional(method.getName()))
            return wrapInvocation(method,args);
        else
            return method.invoke(instance,args);
    }

    protected Object wrapInvocation(Method method, Object[] args) {
        return application.doWithThreadLocals(() -> {
            try {
                return method.invoke(instance,args);
            }
            catch (IllegalAccessException  | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected boolean methodIsTransactional(String name) {
        return methodFilter.size() == 0 || methodFilter.contains(name);
    }

}
