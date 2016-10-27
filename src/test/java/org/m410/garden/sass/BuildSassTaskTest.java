package org.m410.garden.sass;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

/**
 * @author Michael Fortin
 */
public class BuildSassTaskTest {
    @Test
    public void downloadNode() throws Exception {
        BuildSassTask task = new BuildSassTask();
        final Path destination = Paths.get("src/test/assets/node-v6.8.1-darwin-x64.tar.gz");
        final Path sassSource = Paths.get("src/test/assets");
        final String nodeVersion = "v6.8.1";
        BuildSassTask.Node exe = task.downloadNode(destination, nodeVersion, "darwin-x64", sassSource);
        System.out.println(exe.getExecutable().getAbsolutePath());
        assertTrue(exe.getExecutable().exists());
        System.out.println(exe.exec("init","--yes"));
        System.out.println(exe.exec("install", "--save", "lodash@4.16.4"));
    }
}