package org.m410.garden.zone;

import org.m410.garden.application.Application;
import org.m410.garden.zone.transaction.TransactionHandler;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author Michael Fortin
 */
public final class ZoneManager {

    private final List<ZoneFactory> zoneFactories;

    public ZoneManager(List<ZoneFactory> zoneFactories) {
        this.zoneFactories = zoneFactories;
    }

    public ZoneFactory byName(String name) {
        return zoneFactories.stream().filter(zoneFactory -> zoneFactory.name().equals(name)).findFirst().orElse(null);
    }

    public List<ZoneFactory> getZoneFactories() {
        return zoneFactories;
    }

    //
//     /**
//     * Wrap services in a transaction.  Actions do not have to be transactional, you have
//     * the option to set the transaction boundries at the service level by wrapping its
//     * declaration with this method.
//     *
//     * <pre>
//     *     MyService myService = transactional(MyService.class,new MyServiceImpl());
//     * </pre>
//     *
//     * @param intrface the Class for the interface that is proxied.
//     * @param instance an instance of the interface that is invoked.
//     * @param methods names of method to wrap in a transaction, if all transaction are
//     *                wrapped in a transaction, leave this empty.
//     * @param <T> the type of the class and instance to proxy
//     * @return a proxy for the instance.
//    */
//    @SuppressWarnings("unchecked")
//    protected <T> T transactional(Class<T> intrface, T instance, String... methods) {
//        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        final Class[] interfaces = {intrface};
//        final TransactionHandler<T> handler = new TransactionHandler<T>(instance, methods, this);
//
//        return (T) Proxy.newProxyInstance(loader, interfaces, handler);
//    }

    /**
     * Wraps action invocations with a thread local context.
     *
     * @param work internal closure to wrap the action.
     * @return when it's called by the filter or an action this can and should return null, when
     *  it's called to wrap a service call, it should be the the result of the method invocation.
     * @throws Exception everything by default.
     */
    public final Object doInZone(Application.Work work) throws Exception {
        return doWithThreadLocal(zoneFactories, work);
    }

    /**
     * This is part of the plumbing of the application that you shouldn't need to change.  It's
     * called by the application to wrap each request within a thread local context.
     *
     * @param tlf   list of ThreadLocalFactory objects
     * @param block an internal worker closure.
     * @return in most cases it will be null, except when wrapping the call to a service method.
     * @throws Exception everything by default.
     */
    Object doWithThreadLocal(List<? extends ZoneFactory> tlf, Application.Work block) throws Exception {
        if (tlf != null && tlf.size() >= 1) {
            Zone zone = tlf.get(tlf.size() - 1).makeZone();
            zone.start();

            try {
                return doWithThreadLocal(tlf.subList(0, tlf.size() - 1), block);
            }
            finally {
                zone.stop();
            }
        }
        else {
            return block.get();
        }
    }
}
