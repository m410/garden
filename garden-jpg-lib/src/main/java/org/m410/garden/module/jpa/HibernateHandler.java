package org.m410.garden.module.jpa;

import org.m410.garden.zone.ZoneManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Michael Fortin
 */
public final class HibernateHandler<T> implements InvocationHandler {

    private final T instance;
    private final List<String> methodFilter;
    private final ZoneManager zoneManager;

    HibernateHandler(T instance, String[] methodFilter, ZoneManager zoneManager) {
        this.instance = instance;
        this.methodFilter = Arrays.asList(methodFilter);
        this.zoneManager = zoneManager;
    }

    HibernateHandler(T instance, ZoneManager zoneManager) {
        this.instance = instance;
        this.methodFilter = new ArrayList<>();
        this.zoneManager = zoneManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (methodIsTransactional(method.getName())) {
            return wrapInvocation(method, args);
        }
        else {
            return method.invoke(instance, args);
        }
    }

    Object wrapInvocation(Method method, Object[] args) throws Exception {
        return zoneManager.doInZone(() -> method.invoke(instance, args));
    }

    boolean methodIsTransactional(String name) {
        return methodFilter.size() == 0 || methodFilter.contains(name);
    }

}
