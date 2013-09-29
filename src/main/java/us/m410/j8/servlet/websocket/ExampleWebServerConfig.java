package us.m410.j8.servlet.websocket;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Set;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ExampleWebServerConfig implements ServerApplicationConfig {
    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> classes) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> classes) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
