package org.m410.j8.module.jpa.impl;

/**
 * Implements an optimistic locking primary key for persistent entity.
 *
 * @author Michael Fortin
 */
abstract public class OptimisticPrimaryKey<T> extends PessimisticPrimaryKey<T> {
    protected Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
