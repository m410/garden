package org.m410.garden.servlet.websocket;

import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Extension;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ExampleServerEndpointConfig implements ServerEndpointConfig {
    @Override
    public Class<?> getEndpointClass() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getPath() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getSubprotocols() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Extension> getExtensions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Configurator getConfigurator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Class<? extends Encoder>> getEncoders() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Class<? extends Decoder>> getDecoders() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, Object> getUserProperties() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
