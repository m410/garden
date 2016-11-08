package org.m410.garden.jetty9;


import org.m410.fabricate.builder.BuildContext;
import org.m410.fabricate.builder.Task;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author m410
 */
public final class Jetty9Task implements Task {

    String applicationClass;
    String appLoaderClass;
    File sourceDir;
    File classesDir;
    List<URL> classpath;

    @Override
    public String getName() {
        return "jetty9";
    }

    @Override
    public String getDescription() {
        return "Jetty9 reloading server";
    }

    @Override
    public void execute(BuildContext context) throws Exception {
        context.cli().debug("run jetty9");
        context.cli().debug("context:"+context);
        context.cli().debug("context.app:"+context.getApplication());
        context.cli().debug("context.classpath:"+context.getClasspath().get("compile"));

        applicationClass = context.getApplication().getApplicationClass();
        appLoaderClass = applicationClass + "Loader";
        sourceDir = FileSystems.getDefault().getPath(context.getConfiguration().getString("build.source_dir")).toFile();
        classesDir = FileSystems.getDefault().getPath(context.getConfiguration().getString("build.source_output_dir")
                                                     ).toFile();
        classpath = toPath(context.getClasspath().get("compile"));

        final List<URL> jetty9Classpath = toPath(context.getClasspath().get("jetty9"));
        context.cli().debug("jetty9.classpath:"+jetty9Classpath);
        final String webDir = context.getConfiguration().getString("build.webappOutput");
        new DeligateApp(jetty9Classpath, webDir, context.environment());
    }

    class DeligateApp implements Runnable {
        URLClassLoader loader;
        String webappDirPath;
        String env;

        DeligateApp(List<URL> urls, String webappDirPath, String env) throws InterruptedException {
            this.webappDirPath = webappDirPath;
            this.env = env;
            this.loader = new URLClassLoader(urls.toArray(new URL[urls.size()]),null);
            final Thread thread = new Thread(this);
            thread.setContextClassLoader(loader);
            thread.start();
            thread.join();
        }

        @Override @SuppressWarnings("unchecked") public void run() {
            try {
                Class serverClass = loader.loadClass("org.eclipse.jetty.server.Server");
                Object server = serverClass.getConstructor(Integer.TYPE).newInstance(8080);

                Class webAppContextClass = loader.loadClass("org.eclipse.jetty.webapp.WebAppContext");
                Object webAppContext = webAppContextClass.newInstance();

                String path = new File(webappDirPath).getAbsolutePath();
                webAppContextClass.getMethod("setResourceBase",String.class).invoke(webAppContext,path);
                webAppContextClass.getMethod("setContextPath",String.class).invoke(webAppContext,"/");
                webAppContextClass.getMethod("setInitParameter",String.class,String.class).invoke(webAppContext,"m410-env",env);

                Class proxyClass = loader.loadClass("org.m410.garden.jetty9.internal.ProxyServletContainerListener");
                Object listener = proxyClass.newInstance();
                listener = proxyClass.getMethod("withApplicationClassName",String.class).invoke(listener,applicationClass);
                listener = proxyClass.getMethod("withLoaderClassName",String.class).invoke(listener,appLoaderClass);
                listener = proxyClass.getMethod("withSourceDir",File.class).invoke(listener,sourceDir);
                listener = proxyClass.getMethod("withClassesDir",File.class).invoke(listener,classesDir);
                listener = proxyClass.getMethod("withRunPath",List.class).invoke(listener,classpath);
                listener = proxyClass.getMethod("withCompilePath",List.class).invoke(listener,classpath);
                listener = proxyClass.getMethod("withEnv",String.class).invoke(listener,env);
                listener = proxyClass.getMethod("init").invoke(listener);


                Class listenerClass = loader.loadClass("java.util.EventListener");
                webAppContextClass.getMethod("addEventListener",listenerClass).invoke(webAppContext,listener);

                Class handlerClass = loader.loadClass("org.eclipse.jetty.server.Handler");
                serverClass.getMethod("setHandler",handlerClass).invoke(server,webAppContext);
                serverClass.getMethod("start").invoke(server);
                serverClass.getMethod("join").invoke(server);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<URL> toPath(String runtime) {
        return Arrays.stream(runtime.split(System.getProperty("path.separator")))
                .map(s -> {
                    try {
                        return new URL("file://" + s);
                    }
                    catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

    }
}
