package org.m410.garden.module.jpa.impl;

import org.m410.garden.module.jpa.JpaZone;

import java.util.List;
import java.util.Optional;

/**
 * Can be used for common functions of a dao object.
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
        return Optional.ofNullable(JpaZone.get().find(tClass, id));
    }

    @SuppressWarnings("unchecked")
    public List<T> list() {
        return JpaZone.get().createQuery(listQuery).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> page(int start, int limit) {
        return JpaZone.get()
                .createQuery(listQuery)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    public Number count() {
        return (Number) JpaZone.get().createQuery(countQuery).getSingleResult();
    }

    public T insert(T t) {
        return JpaZone.get().merge(t);
    }

    public T update(T t) {
        return JpaZone.get().merge(t);
    }

    @Override
    public void delete(T t) {
        JpaZone.get().remove(t);
    }
}
