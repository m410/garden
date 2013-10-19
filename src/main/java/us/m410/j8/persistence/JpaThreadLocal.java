package us.m410.j8.persistence;

import us.m410.j8.application.SessionStartStop;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class JpaThreadLocal implements SessionStartStop<EntityManager> {

    private static ThreadLocal<EntityManager> threadLocal = new ThreadLocal<>();

    public static EntityManager get() {
        return threadLocal.get();
    }

    @Override
    public void start() {
        threadLocal.set(new EntityManager());
    }

    @Override
    public void stop() {
        EntityManager entityManager = threadLocal.get();
        threadLocal.remove();
    }
}
