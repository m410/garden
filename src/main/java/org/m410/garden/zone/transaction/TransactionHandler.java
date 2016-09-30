package org.m410.garden.zone.transaction;

import org.m410.garden.application.Application;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

    public TransactionHandler(T instance) {
        this.instance = instance;
        this.methodFilter = new ArrayList<>();
        this.application = null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(methodIsTransactional(method.getName()))
            return wrapInvocation(method,args);
        else
            return method.invoke(instance,args);
    }

    protected Object wrapInvocation(Method method, Object[] args) throws Exception{
        return application.getZoneManager().doInZone(() -> method.invoke(instance,args));
    }

    protected boolean methodIsTransactional(String name) {
        return methodFilter.size() == 0 || methodFilter.contains(name);
    }

}
