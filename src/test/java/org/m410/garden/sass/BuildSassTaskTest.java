package org.m410.garden.sass;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Michael Fortin
 */
public class BuildSassTaskTest {
    @Test
    public void downloadNode() throws Exception {
        BuildSassTask task = new BuildSassTask();
        final String destination = "src/test/assets/node-v6.8.1-darwin-x64.tar.gz";
        final String nodeVersion = "v6.8.1";
        BuildSassTask.Node exe = task.downloadNode(destination,nodeVersion);
        System.out.println(exe.getExecutable().getAbsolutePath());
        assertTrue(exe.getExecutable().exists());
        System.out.println(exe.exec("init","--yes"));
        System.out.println(exe.exec("install", "--save", "lodash@4.16.4"));
    }
}