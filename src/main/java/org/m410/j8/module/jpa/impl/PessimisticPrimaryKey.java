package org.m410.j8.module.jpa.impl;

/**
 * Implements an pessimistic locking primary key for persistent entity.
 *
 * @author Michael Fortin
 */
abstract public class PessimisticPrimaryKey<T> implements Id<T>{
    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

}
