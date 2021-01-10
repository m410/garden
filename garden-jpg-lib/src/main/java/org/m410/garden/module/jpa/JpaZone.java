package org.m410.garden.module.jpa;

import org.m410.garden.zone.Zone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * This is an implementation of the thread local design pattern to make
 * the entity manager accessible to a request thread.  It's created by
 * the ThreadLocalSessionFactory class which is injected into the
 * application by the JpaModule.
 *
 * @author Michael Fortin
 */
public final class JpaZone implements Zone<EntityManager> {
    static final Logger log = LoggerFactory.getLogger(JpaZone.class);
    static final ThreadLocal<EntityManager> threadLocal = new ThreadLocal<>();

    public static final String JPA_ZONE = "JPA";

    private final EntityManagerFactory factory;


    public JpaZone(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public static EntityManager get() {
        return threadLocal.get();
    }

    @Override
    public void start() {
        final EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        threadLocal.set(entityManager);
    }

    @Override
    public void stop() {
        EntityManager entityManager = threadLocal.get();
        log.debug("close entityManager: {}", entityManager);

        if(entityManager.getTransaction().isActive())
            entityManager.getTransaction().commit();

        threadLocal.remove();
    }
}
