package us.m410.j8.action;

import java.io.OutputStream;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface ResponseStream {
    void stream(OutputStream out);
}
