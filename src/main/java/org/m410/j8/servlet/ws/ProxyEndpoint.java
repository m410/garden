package org.m410.j8.servlet.ws;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

/**
 * This is a proxy to the internal controller
 *
 * @author Michael Fortin
 */
public class ProxyEndpoint extends Endpoint {

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {

    }
}
