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
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Creates the orm.xml file from the builders defined in the project.
 *
 * @author Michael Fortin
 */
public final class OrmXmlTask implements Task {
    // todo add meta model generator
    // todo http://stackoverflow.com/questions/3037593/how-to-generate-jpa-2-0-metamodel

    final String org = "org.m410.garden";
    final String name = "garden-jpa";

    @Override
    public String getName() {
        return "orm-xml";
    }

    @Override
    public String getDescription() {
        return "Generate the orm.xml persistence configuration file";
    }

    @Override
    public void execute(BuildContext context) throws Exception {
        context.cli().debug("build orm xml");
        final String[] dependencies = context.getClasspath()
                .get("compile")
                .split(System.getProperty("path.separator"));

        Collection<File> classpath = Arrays.stream(dependencies)
                .map(File::new)
                .collect(Collectors.toList());
        classpath.add(Paths.get(context.getConfiguration().getString("build.source_output_dir")).toFile());

        final String sourceOut = context.getConfiguration().getString("build.source_output_dir");
        final Path outputPath = FileSystems.getDefault().getPath(sourceOut, "META-INF/orm.xml");
        final File file = FileSystems.getDefault().getPath(sourceOut, "META-INF").toFile();

        if (!file.exists() && !file.mkdirs())
            throw new RuntimeException("Could not make META-INF directories");

        final Optional<ImmutableHierarchicalConfiguration> optCfg = context.configAt(org, name);

        if (!optCfg.isPresent()) {
            throw new RuntimeException("could not find configuration");
        }

        final ImmutableHierarchicalConfiguration c = optCfg.get();

        new ReflectConfigFileBuilder(c.getString("orm_builder_class"))
                .withClasspath(classpath)
                .withPath(outputPath)
                .withEnv(context.environment())
                .withAppCreated(true)
                .withAppClass(context.getConfiguration().getString("application.applicationClass"))
                .withFactoryClass(c.getString("factory_class"))
                .make();
    }

    protected boolean isTrue(Object o) {
        if (o instanceof Boolean)
            return (Boolean) o;
        else if (o instanceof String)
            return Boolean.valueOf((String) o);
        else
            return false;
    }
}
