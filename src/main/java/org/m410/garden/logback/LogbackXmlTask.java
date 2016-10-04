package org.m410.garden.logback;


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
 * Creates the logback.xml file from the configuration.m410.xml.
 *
 * @author Michael Fortin
 */
public final class LogbackXmlTask implements Task{
    final String org = "org.m410.garden";
    final String name = "garden-logback";

    @Override
    public String getName() {
        return "logback-xml";
    }

    @Override
    public String getDescription() {
        return "generate the logback xml configuration file";
    }

    @Override
    public void execute(BuildContext context) throws Exception {
        Collection<File> mavenProject = Arrays.stream(
                context.getClasspath().get("compile").split(System.getProperty("path.separator")))
                .map(File::new)
                .collect(Collectors.toList());

        final String sourceDir = context.getBuild().getSourceOutputDir();
        final Path outputPath = FileSystems.getDefault().getPath(sourceDir, "logback.xml");

        final Optional<ImmutableHierarchicalConfiguration> optCfg = context.configAt(org, name);

        if (!optCfg.isPresent()) {
            throw new RuntimeException("could not find configuration");
        }

        final ImmutableHierarchicalConfiguration c = optCfg.get();

        new ReflectConfigFileBuilder(c.getString("builder_class"))
                .withClasspath(mavenProject)
                .withPath(outputPath)
                .withEnv(context.environment())
                .withAppClass(context.getConfiguration().getString("application.applicationClass"))
                .withFactoryClass(c.getString("factory_class"))
                .make();
    }
}
