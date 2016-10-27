package org.m410.garden.javascript;

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
import java.util.*;

/**
 * @author Michael Fortin
 */
public class BuildJavascriptTask implements Task {
    @Override
    public String getName() {
        return "javascript-manager";
    }

    @Override
    public String getDescription() {
        return "Javascript dependency manager";
    }

    @Override
    public void execute(BuildContext buildContext) throws Exception {
        final ImmutableHierarchicalConfiguration config = buildContext.configAt("org.m410.garden", "garden-sass")
                .orElseThrow(()->new RuntimeException("Could not find configuration"));

        // todo downloading node should be separate task
        final String nodeVersion = config.getString("node_version", "v6.8.1");
        final String arch = buildContext.getConfiguration().getString("build.osName");
        final String base = buildContext.getConfiguration().getString("build.cacheDir");
        final String platform = platform(arch);
        final Path nodeDest = Paths.get(base).resolve("node=" + nodeVersion + "-" + platform + ".tar.gz");
        final Path jsSource = Paths.get(buildContext.getConfiguration().getString("build.webappDir"));

        Node node = downloadNode(nodeDest, nodeVersion, platform, jsSource).init();

        // todo also should check for changes instead of running this each time
        config.getList(String.class, "dependencies", new ArrayList<>()).forEach(dep -> {
            node.exec("install", "--save", dep);
        });

    }

    private String platform(String arch) {
        if (arch.startsWith("Mac")) {
            return "darwin-x64";
        }
        else {
            return "win-x64";
        }
    }

    Node downloadNode(Path destination, String nodeVersion, String platform, Path jsSource) throws IOException,
            ArchiveException {
        final String distName = "node-" + nodeVersion + "-" + platform;

        File nodeTar = destination.toFile();
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
                jsSource.toFile()
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
