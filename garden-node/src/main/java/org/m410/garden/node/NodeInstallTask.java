package org.m410.garden.node;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.fabricate.builder.BuildContext;
import org.m410.fabricate.builder.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Michael Fortin
 */
public final class NodeInstallTask implements Task {
    @Override
    public String getName() {
        return "install-node";
    }

    @Override
    public String getDescription() {
        return "Install Node for use in project";
    }

    @Override
    public void execute(BuildContext buildContext) throws Exception {
        buildContext.cli().debug("node install");
        final ImmutableHierarchicalConfiguration config = buildContext.getConfiguration();
        final String nodeVersion = config.getString("build.node_version", "v7.1.0");
        final String arch = buildContext.getConfiguration().getString("build.os_name");
        final String base = buildContext.getConfiguration().getString("build.cache_dir");
        final String platform = platform(arch);
        final String distName = "node-" + nodeVersion + "-" + platform + ".tar.gz";

        final Path nodeDest = Paths.get(base).resolve(distName);

        File nodeTar = nodeDest.toFile();
        nodeTar.getParentFile().mkdirs();
        final File nodeParent = nodeTar.getParentFile();
        final File distDir = new File(nodeParent, distName);

        if(!distDir.exists()) {
            URL url = new URL("https://nodejs.org/dist/" + nodeVersion + "/" + distName);
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(nodeTar);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            final List<File> files = TarGz.unTar(TarGz.unGzip(nodeTar, nodeTar.getParentFile()), nodeParent);

            if (!files.get(0).renameTo(Paths.get(base).resolve("node").toFile())) {
                throw new RuntimeException(files.get(0).getAbsolutePath() + " could not be renamed");
            }

        }
    }

    private String platform(String arch) {
        if (arch.startsWith("Mac")) {
            return "darwin-x64";
        }
        else {
            return "win-x64";
        }
    }
}
