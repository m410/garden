package org.m410.garden.di;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;

import javax.servlet.http.HttpServlet;
import java.util.Collection;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ServletSupplier {
    Collection<? extends HttpServlet> get(ImmutableHierarchicalConfiguration configuration);
}
