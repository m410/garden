package org.m410.garden.servlet.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.net.URI;
import java.util.Set;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ExampleWebSocketContainer implements ServerContainer {
    @Override
    public void addEndpoint(ServerEndpointConfig serverEndpointConfig) throws DeploymentException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addEndpoint(Class<?> aClass) throws DeploymentException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public long getDefaultAsyncSendTimeout() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAsyncSendTimeout(long l) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Session connectToServer(Object o, URI uri) throws DeploymentException, IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Session connectToServer(Class<?> aClass, URI uri) throws DeploymentException, IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Session connectToServer(Endpoint endpoint, ClientEndpointConfig clientEndpointConfig, URI uri) throws DeploymentException, IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Session connectToServer(Class<? extends Endpoint> aClass, ClientEndpointConfig clientEndpointConfig, URI uri) throws DeploymentException, IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getDefaultMaxSessionIdleTimeout() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDefaultMaxSessionIdleTimeout(long l) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getDefaultMaxBinaryMessageBufferSize() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDefaultMaxBinaryMessageBufferSize(int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getDefaultMaxTextMessageBufferSize() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setDefaultMaxTextMessageBufferSize(int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set<Extension> getInstalledExtensions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
