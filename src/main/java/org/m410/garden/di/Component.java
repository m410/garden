package org.m410.garden.di;

import java.util.List;

/**
 * @author Michael Fortin
 */
public interface Component {
    List<ComponentBuilder> builders();
}
