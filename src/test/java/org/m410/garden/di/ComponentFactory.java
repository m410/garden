package org.m410.garden.di;


/**
 * @author Michael Fortin
 */
public interface ComponentFactory<T> {
    T make(TransactionProxy txmgr, Components dependencies);
}
