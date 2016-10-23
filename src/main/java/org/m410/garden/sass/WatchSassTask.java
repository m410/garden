package org.m410.garden.sass;

import org.apache.commons.configuration2.ImmutableHierarchicalConfiguration;
import org.m410.fabricate.builder.BuildContext;
import org.m410.fabricate.builder.Cli;
import org.m410.fabricate.builder.Task;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * @author Michael Fortin
 */
public class WatchSassTask implements Task{
    @Override
    public String getName() {
        return "sass-watcher";
    }

    @Override
    public String getDescription() {
        return "Watches sass files for changes, then compiles";
    }

    @Override
    public void execute(final BuildContext buildContext) throws Exception {
        final ImmutableHierarchicalConfiguration config = buildContext.configAt("org.m410.garden", "garden-sass")
                .orElseThrow(()->new RuntimeException("Could not find configuration"));

        Path source = Paths.get(config.getString("source"));
        Path output = Paths.get(buildContext.getConfiguration().getString("build.webappOutput"))
                .resolve(config.getString("output"));
        final SassCompiler compiler = new SassCompiler(source,output, buildContext.cli());

        new Thread(new Watcher(source, compiler, buildContext.cli())).run();
    }

    private class Watcher implements Runnable {
        private final SassCompiler compiler;
        private final Cli cli;
        private final Path source;

        public Watcher(Path source, SassCompiler compiler, Cli cli) {
            this.compiler = compiler;
            this.cli = cli;
            this.source = source;
        }

        @Override
        public void run() {
                try {
                    WatchService watcher = FileSystems.getDefault().newWatchService();
                    source.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

                    while(true) {
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
                            Path fileName = ev.context();

                            cli.info(kind.name() + ": " + fileName);
                            compiler.compile();
                        }
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
