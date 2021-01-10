package org.m410.garden.controller;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author m410
 */
public interface MockServletInput {
    default ServletInputStream servletInputStream(String content) {
        final InputStream byteIn = new ByteArrayInputStream(content.getBytes());

        return new ServletInputStream() {
            @Override public boolean isFinished() { return false; }
            @Override public boolean isReady() { return true; }
            @Override public void setReadListener(ReadListener readListener) { }

            @Override public int read() throws IOException {
                return byteIn.read();
            }
        };
    }

    default ServletOutputStream servletOutputStream(StringBuffer sb) {
        return new ServletOutputStream() {
            @Override public boolean isReady() { return true; }
            @Override public void setWriteListener(WriteListener writeListener) { }
            @Override public void write(int b) throws IOException {
                sb.append((char)b);
            }
        };
    }
}
