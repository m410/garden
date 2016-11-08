package org.m410.garden.jetty9;

import org.m410.fabricate.builder.BuildContext;
import org.m410.fabricate.builder.Cli;
import org.m410.fabricate.builder.Task;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardWatchEventKinds.*;

/**
 * @author Michael Fortin
 */
public final class WatchStaticTask implements Task {
    @Override
    public String getName() {
        return "watch-static";
    }

    @Override
    public String getDescription() {
        return "watches static files for changes, then moves to runtime directory";
    }

    @Override
    public void execute(BuildContext buildContext) throws Exception {
        buildContext.cli().debug("watch static");
        String staticResource = buildContext.getConfiguration().getString("build.webappDir");
        String outputResources = buildContext.getConfiguration().getString("build.webappOutput");
        Watcher watcher = new Watcher(Paths.get(staticResource), Paths.get(outputResources), buildContext.cli());
        new Thread(watcher).run();
    }


    private class Watcher implements Runnable {
        private final Cli cli;
        private final Path source;
        private final Path destination;

        Watcher(Path source, Path dest, Cli cli) {
            this.cli = cli;
            this.source = source;
            this.destination = dest;
        }

        @Override
        public void run() {
            try {
                WatchService watcher = FileSystems.getDefault().newWatchService();
                source.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

                while (true) {
                    WatchKey key;

                    try {
                        key = watcher.take();
                    }
                    catch (InterruptedException ex) {
                        return;
                    }

                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();
                        @SuppressWarnings("unchecked")
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path changed = ev.context();
                        final Path relativize = changed.relativize(source);
                        final Path target = destination.resolve(relativize);
                        cli.info(kind.name() + ": " + changed);
                        cli.info("copy to: " + target);
                        Files.copy(changed, target, REPLACE_EXISTING);
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
