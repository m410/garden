package org.m410.garden.module.ormbuilder.orm;


import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;

import java.nio.file.Path;

/**
 * This is used to use a common interface to write out configuration files.  It's
 * call via refection so it's really not necessary, it's more of a mental note.
 *
 * @author Michael Fortin
 */
public interface ConfigFileBuilder {
    void writeToFile(Path path, ImmutableHierarchicalConfiguration configuration);
}
