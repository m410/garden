package org.m410.garden.sass;

import io.bit3.jsass.Options;
import io.bit3.jsass.Output;
import org.m410.fabricate.builder.Cli;

import java.io.FileWriter;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

/**
 * @author Michael Fortin
 */
final class SassCompiler {
    private final Path sassFile;
    private final Path outputCss;
    private final Cli cli;

    SassCompiler(Path sassFile, Path outputCss, Cli cli) {
        this.sassFile = sassFile;
        this.outputCss = outputCss;
        this.cli = cli;
    }

    void compile() {
        URI inputUri = sassFile.toFile().toURI();
        URI outputUri = outputCss.toFile().toURI();

        final Path outputSourceMap = Paths.get(outputCss.toFile().getAbsolutePath()+".map");
        URI sourceMapUri = outputSourceMap.toFile().toURI();

        cli.debug("in:"+inputUri+", out:"+ outputUri);

        try {
            final io.bit3.jsass.Compiler compiler = new io.bit3.jsass.Compiler();

            final Options options = new Options();
            options.setSourceMapEmbed(true);
            options.setSourceMapFile(sourceMapUri);
            options.setIncludePaths(Collections.singletonList(sassFile.toFile().getParentFile()));
            final Output output = compiler.compileFile(inputUri, outputUri, options);

            try (FileWriter w = new FileWriter(outputCss.toFile())) {
                w.write(output.getCss());
            }

            try (FileWriter w = new FileWriter(outputSourceMap.toFile())) {
                w.write(output.getSourceMap());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
