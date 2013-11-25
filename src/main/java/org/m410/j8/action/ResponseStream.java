package org.m410.j8.action;

import java.io.OutputStream;

/**
 * An output stream closure used in the Response object.
 *
 * @author Michael Fortin
 */
public interface ResponseStream {
    void stream(OutputStream out);
}
