package org.m410.garden.di;

import org.m410.garden.configuration.Configuration;
import org.m410.garden.controller.HttpCtlr;

import java.util.List;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ControllerSupplier {
    List<? extends HttpCtlr> get(Configuration configuration, Components components);
}
