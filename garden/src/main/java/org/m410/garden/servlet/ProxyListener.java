package org.m410.garden.servlet;

import java.util.EventListener;

/**
 * @author m410
 */
public interface ProxyListener extends EventListener {
    void setDelegateClass(String name);
}
