package org.m410.j8.servlet.ws;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Set;

/**
 * add to the application as a listener, configures endpoints based on controller paths.
 *
 *
 * @author Michael Fortin
 */
public class ServerListenerConfig implements ServerApplicationConfig {

    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> classes) {
        return null;
    }

    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> classes) {
        return null;
    }
}
