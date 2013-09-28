package us.m410.j8.servlet;

import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.List;
import java.util.Map;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ExampleWebSocketConfig implements EndpointConfig {
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
