package org.m410.garden.sass;

import org.junit.Test;
import org.m410.fabricate.builder.Cli;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

/**
 * @author Michael Fortin
 */
public class SassCompilerTest {
    @Test
    public void compile() throws Exception {
        final Path sass = Paths.get("src/test/assets/test.scss");
        final Path css = Paths.get("src/test/assets/output.css");
        final Path cssMap = Paths.get("src/test/assets/output.css.map");

        SassCompiler compiler = new SassCompiler(sass,css, cli);
        compiler.compile();
        assertTrue(css.toFile().exists());
        assertTrue(cssMap.toFile().exists());
        css.toFile().delete();
        cssMap.toFile().delete();
    }

    Cli cli = new Cli(){
        @Override
        public String ask(String s) {
            return null;
        }

        @Override
        public void warn(String s) {
            System.out.println(s);
        }

        @Override
        public void info(String s) {
            System.out.println(s);
        }

        @Override
        public void debug(String s) {
            System.out.println(s);
        }

        @Override
        public void error(String s) {
            System.out.println(s);
        }

        @Override
        public void println(String s) {
            System.out.println(s);
        }
    };
}