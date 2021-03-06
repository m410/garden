package org.m410.garden.sass;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.fabricate.builder.BuildContext;
import org.m410.fabricate.builder.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @author Michael Fortin
 */
public final class BuildSassTask implements Task {
    @Override
    public String getName() {
        return "sass-compile";
    }

    @Override
    public String getDescription() {
        return "Compiles Sass to css";
    }

    @Override
    public void execute(BuildContext buildContext) throws Exception {
        buildContext.cli().debug("build sass");
        final ImmutableHierarchicalConfiguration buildConfig = buildContext.getConfiguration();
        final ImmutableHierarchicalConfiguration config = buildContext.configAt("org.m410.garden", "garden-sass")
                .orElseThrow(()->new RuntimeException("Could not find configuration"));

        final Path sourceFile = Paths.get(config.getString("source"));
        final File executable = Paths.get(config.getString("node_base")).resolve(config.getString("npm")).toFile();
        final File workingDir = sourceFile.getParent().toFile();
        Node node = new Node(executable, workingDir).init();

        IntStream.range(0, config.getMaxIndex("dependencies")).forEach(idx -> {
            String name = config.getString("dependencies(" + idx + ").name");
            String version = config.getString("dependencies(" + idx + ").version");
            node.exec("install", name + "@" + version, "--save");
        });

        Path output = Paths.get(buildContext.getConfiguration().getString("build.webapp_output"))
                .resolve(config.getString("output"));
        output.getParent().toFile().mkdirs();

        new SassCompiler(sourceFile, output, buildContext.cli()).compile();
    }

    static class Node {
        private final File executable;
        private final File workingDir;
        private final EnumSet<PosixFilePermission> ownerExecute = EnumSet.of(
                PosixFilePermission.OWNER_EXECUTE,
                PosixFilePermission.OWNER_READ,
                PosixFilePermission.OWNER_WRITE,
                PosixFilePermission.GROUP_READ,
                PosixFilePermission.GROUP_WRITE,
                PosixFilePermission.OTHERS_READ,
                PosixFilePermission.OTHERS_WRITE);

        Node(File executable, File workingDir) {
            this.executable = executable;
            this.workingDir = workingDir;

            try {
                Files.setPosixFilePermissions(executable.toPath(), ownerExecute);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        File getExecutable() {
            return executable;
        }

        String exec(String... args) {
            LinkedList<String> list = new LinkedList<>(Arrays.asList(args));
            list.addFirst(executable.getAbsolutePath());

            try {
                Process process = new ProcessBuilder()
                        .directory(workingDir)
                        .command(list.toArray(new String[list.size()]))
                        .redirectError(new File("process.error.txt"))
                        .start();

                Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\\A");
                return scanner.hasNext() ? scanner.next() : "";
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        boolean packageExists() {
            return workingDir.toPath().resolve("package.json").toFile().exists();
        }

        Node init() {
            if (!packageExists()) {
                exec("init", "--yes");
            }

            return this;
        }
    }
}
