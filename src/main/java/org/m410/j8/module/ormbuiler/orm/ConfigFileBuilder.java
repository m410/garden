package org.m410.j8.module.ormbuiler.orm;


import org.m410.j8.configuration.Configuration;

import java.nio.file.Path;

/**
 * This is used to use a common interface to write out configuration files.  It's
 * call via refection so it's really not necessary, it's more of a mental note.
 *
 * @author Michael Fortin
 */
public interface ConfigFileBuilder {
    void writeToFile(Path path, Configuration configuration);
}
