package org.m410.j8.persistence.help;

import org.m410.j8.persistence.EntityManager;
import org.m410.j8.persistence.JpaThreadLocal;

import java.util.Optional;
import java.util.Set;

/**
 * If your concerned about tightly cupling your code to this framework don't use this.
 *
 * @author Michael Fortin
 */
public interface Dao<T extends Id<K>, K> {

    default public Optional<T> get(K id) {
        EntityManager entityManager = JpaThreadLocal.get();
        return Optional.empty();//entityManager.find(id);
    }

    default public Set<T> list() {
        EntityManager entityManager = JpaThreadLocal.get();
        return null;//entityManager.find(id);
    }

    default public Set<T> page(int start, int limit) {
        EntityManager entityManager = JpaThreadLocal.get();
        return null;//entityManager.find(id);
    }

    default public long count() {
        EntityManager entityManager = JpaThreadLocal.get();
        return 0;//entityManager.find(id);
    }

    default public T insert(T t) {
        EntityManager entityManager = JpaThreadLocal.get();
        return null;//entityManager.find(id);
    }

    default public T update(T t) {
        EntityManager entityManager = JpaThreadLocal.get();
        return null;//entityManager.find(id);
    }
}
