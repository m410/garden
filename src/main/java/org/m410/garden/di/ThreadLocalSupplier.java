package org.m410.garden.di;

import org.m410.garden.configuration.Configuration;
import org.m410.garden.transaction.ThreadLocalSessionFactory;

import javax.servlet.http.HttpServlet;
import java.util.Collection;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ThreadLocalSupplier {
    Collection<? extends ThreadLocalSessionFactory> get(Configuration configuration);
}
