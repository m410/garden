package org.m410.garden.di;

import org.m410.garden.configuration.Configuration;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.util.Collection;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface FilterSupplier {
    Collection<? extends Filter> get(Configuration configuration);
}
