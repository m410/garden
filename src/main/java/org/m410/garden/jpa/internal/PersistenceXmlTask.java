/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.m410.garden.jpa.internal;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.fabricate.builder.BuildContext;
import org.m410.fabricate.builder.Task;
import org.m410.fabricate.util.ReflectConfigFileBuilder;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Uses the persistence defintion from the configuration.m410.xml to create the
 * configuration file.
 *
 * @author Michael Fortin
 */
public final class PersistenceXmlTask implements Task {

    // todo move to config
    final String builderName = "org.m410.garden.module.ormbuilder.persistence.PersistenceXmlBuilder";
    final String org = "org.m410.garden";
    final String name = "garden-jpa";

    @Override
    public String getName() {
        return "persistence-xml";
    }

    @Override
    public String getDescription() {
        return "Generate the persistence.xml configuration file";
    }

    @Override
    public void execute(BuildContext context) throws Exception {
        final String[] compiles = context.getClasspath().get("compile").split(System.getProperty("path.separator"));
        Collection<File> mavenProject = Arrays.stream(compiles).map(File::new).collect(Collectors.toList());
        final Optional<ImmutableHierarchicalConfiguration> optCfg = context.configAt(org, name);

        if (!optCfg.isPresent()) {
            throw new RuntimeException("could not find configuration");
        }

        final ImmutableHierarchicalConfiguration c = optCfg.get();
        final String sourceOut = context.getBuild().getSourceOutputDir();
        final Path outputPath = FileSystems.getDefault().getPath(sourceOut,"META-INF/persistence.xml");
        final File file = FileSystems.getDefault().getPath(sourceOut,"META-INF").toFile();

        if(!file.exists() && !file.mkdirs())
            throw new RuntimeException("Could not make META-INF directories");

        new ReflectConfigFileBuilder(builderName)
                .withClasspath(mavenProject)
                .withAppClass(context.getConfiguration().getString("application.applicationClass"))
                .withFactoryClass(c.getString("factory_class"))
                .withPath(outputPath)
                .withEnv(context.environment())
                .make();
    }
}
