package org.m410.garden.di;

import org.m410.garden.fixtures.MyService;
import org.m410.garden.fixtures.MyServiceImpl;

/**
 * @author Michael Fortin
 */
public interface TransactionProxy {
    <T> T required(Class<T> type, T impl);
}
