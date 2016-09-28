package org.m410.garden.di;

import org.m410.garden.configuration.Configuration;

import javax.servlet.http.HttpServlet;
import java.util.Collection;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ServletSupplier {
    Collection<? extends HttpServlet> get(Configuration configuration);
}
