package org.m410.j8.persistence.help;

import org.m410.j8.persistence.JpaThreadLocal;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * If your concerned about tightly cupling your code to this framework don't use this.
 *
 * @author Michael Fortin
 */
public abstract class AbstractDao<T extends Id<K>, K> implements Dao<T,K> {
    protected final String listQuery;
    protected final String countQuery;
    protected final Class<T> tClass;

    protected AbstractDao(Class<T> tClass) {
        this.tClass = tClass;
        this.listQuery = "from "+tClass.getName()+" T";
        this.countQuery = "select count(id) from "+tClass.getName()+" T";
    }

    public Optional<T> get(K id) {
        return Optional.ofNullable(JpaThreadLocal.get().find(tClass, id));
    }

    public List<T> list() {
        return JpaThreadLocal.get().createQuery(listQuery).getResultList();
    }

    public List<T> page(int start, int limit) {
        return JpaThreadLocal.get()
                .createQuery(listQuery)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    public Number count() {
        return (Number)JpaThreadLocal.get().createQuery(countQuery).getSingleResult();
    }

    public T insert(T t) {
        return JpaThreadLocal.get().merge(t);
    }

    public T update(T t) {
        return JpaThreadLocal.get().merge(t);
    }
}
