package org.m410.garden.di;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.garden.controller.HttpCtlr;

import java.util.List;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ControllerSupplier {
    List<? extends HttpCtlr> get(ImmutableHierarchicalConfiguration configuration, Components components);
}
