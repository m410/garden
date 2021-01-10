package org.m410.garden.module.jpa.impl;

import java.util.List;
import java.util.Optional;

/**
 * A DAO interface of common CRUD Operations.
 *
 * @author Michael Fortin
 */
public interface Dao<T extends Id<K>, K> {
    public Optional<T> get(K id) ;

    public List<T> list() ;

    public List<T> page(int start, int limit);

    public Number count() ;

    public T insert(T t) ;

    public T update(T t) ;

    public void delete(T t) ;
}
