package org.m410.garden.di;

import org.m410.garden.configuration.Configuration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionListener;
import java.awt.*;
import java.util.Collection;
import java.util.EventListener;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface ListenerSupplier {
    Collection<? extends EventListener> get(Configuration configuration);
}
