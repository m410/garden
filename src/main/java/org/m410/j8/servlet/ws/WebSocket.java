package org.m410.j8.servlet.ws;

/**
 * Add a websocket listener to a controller.
 *
 *
 * @author m410
 */
public class WebSocket {
    private CloseHandler closeHandler ;
    private OpenHandler openHandler;
    private ErrorHandler errorHandler;
    private MessageHandler messageHandler;

    WebSocket(CloseHandler closeHandler, OpenHandler openHandler, ErrorHandler errorHandler, MessageHandler messageHandler) {
        this.closeHandler = closeHandler;
        this.openHandler = openHandler;
        this.errorHandler = errorHandler;
        this.messageHandler = messageHandler;
    }

    private WebSocket() {
    }

    public static WebSocket socket() {
        return new WebSocket();
    }

    public WebSocket open(OpenHandler openHandler) {
        return new WebSocket(closeHandler,openHandler,errorHandler,messageHandler);
    }

    public WebSocket close(CloseHandler closeHandler) {
        return new WebSocket(closeHandler,openHandler,errorHandler,messageHandler);
    }

    public WebSocket message(MessageHandler messageHandler) {
        return new WebSocket(closeHandler,openHandler,errorHandler,messageHandler);
    }

    public WebSocket error(ErrorHandler errorHandler) {
        return new WebSocket(closeHandler,openHandler,errorHandler,messageHandler);
    }

}
