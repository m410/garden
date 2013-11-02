package org.m410.j8.persistence;

import org.m410.j8.application.ThreadLocalSession;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class JpaThreadLocal implements ThreadLocalSession<EntityManager> {

    private static ThreadLocal<EntityManager> threadLocal = new ThreadLocal<>();

    private EntityManagerFactory factory;


    public JpaThreadLocal(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public static EntityManager get() {
        return threadLocal.get();
    }

    @Override
    public void start() {
        threadLocal.set(factory.createEntityManager());
    }

    @Override
    public void stop() {
        EntityManager entityManager = threadLocal.get();

        if(entityManager.getTransaction().isActive())
            entityManager.getTransaction().commit();

        threadLocal.remove();
    }
}
