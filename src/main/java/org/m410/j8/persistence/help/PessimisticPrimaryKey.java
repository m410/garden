package org.m410.j8.persistence.help;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
abstract public class PessimisticPrimaryKey<T> implements Id<T>{
    protected T id;

    @javax.persistence.Id
    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

}
