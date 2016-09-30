package org.m410.garden.di;

import java.util.List;

/**
 * @author Michael Fortin
 */
@FunctionalInterface
public interface Component {
    List<ComponentBuilder> builders();
}
