package org.m410.garden.sass;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.fabricate.builder.BuildContext;
import org.m410.fabricate.builder.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
public class BuildSassTask implements Task{
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
        final ImmutableHierarchicalConfiguration config = buildContext.configAt("org.m410.garden", "garden-sass")
                .orElseThrow(()->new RuntimeException("Could not find configuration"));

        // todo load by platform
        // todo use configured path instead of this hardcoded path
        final String destination = ".fab/node-v6.8.1-darwin-x64.tar.gz";
        final String nodeVersion = "v6.8.1";

        if(config.getMaxIndex("packages") >=0) {
            Node node = downloadNode(destination, nodeVersion);

            if(node.packageExists()) {
                node.exec("init", "--yes");
            }

            IntStream.range(0, config.getMaxIndex("packages")).forEach(idx ->{
                try {
                    node.exec("install","--save", config.getString("packages("+idx+").name"));
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Path source = Paths.get(config.getString("source"));
        Path output = Paths.get(buildContext.getConfiguration().getString("build.webappOutput"))
                .resolve(config.getString("output"));
        output.getParent().toFile().mkdirs();

        new SassCompiler(source,output, buildContext.cli()).compile();
    }

    Node downloadNode(String destination, String nodeVersion) throws IOException, ArchiveException {
        final String distName = "node-" + nodeVersion + "-darwin-x64";

        File nodeTar = new File(destination);
        nodeTar.getParentFile().mkdirs();
        final File nodeParent = nodeTar.getParentFile();
        final File distDir = new File(nodeParent, distName);

        if(!distDir.exists()) {
            URL url = new URL("https://nodejs.org/dist/" + nodeVersion + "/" + distName + ".tar.gz");
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(nodeTar);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            TarGz.unTar(TarGz.unGzip(nodeTar, nodeTar.getParentFile()), nodeParent);
        }

        return new Node(
                distDir.toPath().resolve("lib/node_modules/npm/bin/npm-cli.js").toFile(),
                Paths.get("src/test/assets").toFile()
        );
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

        public File getWorkingDir() {
            return workingDir;
        }


        String exec(String... args) throws IOException {
            LinkedList<String> list = new LinkedList<>(Arrays.asList(args));
            list.addFirst(executable.getAbsolutePath());

            System.out.println(list);

            Process process = new ProcessBuilder()
                    .directory(workingDir)
                    .command(list.toArray(new String[list.size()]))
                    .redirectError(new File("process.error.txt"))
                    .start();

            Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }

        public boolean packageExists() {
            return workingDir.toPath().resolve("package.json").toFile().exists();
        }
    }
}
