package org.m410.garden.zone.transactional.fixtures;

import com.google.common.collect.ImmutableList;
import org.m410.garden.zone.ZoneFactory;
import org.m410.garden.zone.ZoneHandlerFactory;
import org.m410.garden.zone.ZoneManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author m410
 */
public final class TrxThreadLocalFactory implements ZoneFactory<Trx> {
    private ZoneManager zoneManager;

    @Override
    public void setZoneManager(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }

    @Override
    public Trx makeZone() {
        return new Trx();
    }

    @Override
    public void shutdown() {
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }

    @Override
    public ZoneHandlerFactory zoneHandlerFactory() {
        return new TrxHandlerFactory(zoneManager);
    }

    class TrxHandlerFactory implements ZoneHandlerFactory {
        private ZoneManager zoneManager;

        TrxHandlerFactory(ZoneManager zoneManager) {
            this.zoneManager = zoneManager;
        }

        @Override
        public <T> T proxy(Class<T> interfce, T instance) {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();
            final Class[] interfaces = {interfce};
            final TrxInvocationHandler handler = new TrxInvocationHandler(instance, ImmutableList.of(), zoneManager);

            return (T) Proxy.newProxyInstance(loader, interfaces, handler);
        }
    }

    class TrxInvocationHandler implements InvocationHandler {
        private final Object instance;
        private final List<String> methodFilter;
        private final ZoneManager zoneManager;

        TrxInvocationHandler(Object instance, List<String> methodFilter, ZoneManager zoneManager) {
            this.instance = instance;
            this.methodFilter = methodFilter;
            this.zoneManager = zoneManager;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            if (methodIsTransactional(method.getName())) {
                return wrapInvocation(method, objects);
            }
            else {
                return method.invoke(o, objects);
            }
        }

        Object wrapInvocation(Method method, Object[] args) throws Exception {
            return zoneManager.doInZone(() -> method.invoke(instance, args));
        }

        boolean methodIsTransactional(String name) {
            return methodFilter.size() == 0 || methodFilter.contains(name);
        }
    }


}
