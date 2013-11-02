package org.m410.j8.persistence.help;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: m410
 * Date: 10/31/13
 * Time: 8:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Dao<T extends Id<K>, K> {
    public Optional<T> get(K id) ;

    public List<T> list() ;

    public List<T> page(int start, int limit);

    public Number count() ;

    public T insert(T t) ;

    public T update(T t) ;
}
