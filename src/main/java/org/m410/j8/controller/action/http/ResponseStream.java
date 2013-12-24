package org.m410.j8.controller.action.http;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An output stream closure used in the Response object.
 *
 * @author Michael Fortin
 */
public interface ResponseStream {
    void stream(OutputStream out) throws IOException;
}
