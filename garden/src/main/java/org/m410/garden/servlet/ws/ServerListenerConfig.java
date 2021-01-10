package org.m410.garden.servlet.ws;

import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    static final Logger log = LoggerFactory.getLogger(ServerListenerConfig.class);

    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> classes) {
        log.warn("get endpoint configs: {}", classes);
        // todo setup endpoint from controllers
        return ImmutableSet.of();
    }

    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> classes) {
        log.warn("get annotated endpoint configs: {}", classes);
        return ImmutableSet.of();
    }
}
