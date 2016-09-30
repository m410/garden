package org.m410.garden.servlet;

import javax.servlet.ServletContext;
import java.util.EventListener;

/**
 * Create a listener class and adds it the the servlet container at runtime.
 *
 * A listener implementation of one of the following.
 * <ul>
 * <li>javax.servlet.ServletRequestListener           </li>
 * <li>javax.servlet.ServletRequestAttributeListener  </li>
 * <li>javax.servlet.ServletContextListener           </li>
 * <li>javax.servlet.ServletContextAttributeListener  </li>
 * <li>javax.servlet.http.HttpSessionListener         </li>
 * <li>javax.servlet.http.HttpSessionAttributeListener</li>
 * <li>javax.servlet.http.HttpSessionAttributeListener</li>
 * </ul>
 * <p>
 * The most common example usage is if you would like to perform some action
 * on session start or session expire.  In those cases add your own implement
 * of the HttpSessionListener here.
 *
 *
 * @author Michael Fortin
 */
public final class ListenerDefinition {

    private String name;
    private String className;

    public ListenerDefinition(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public void configure(ServletContext servletContext) {
        servletContext.addListener(getClassName());
    }

    public void configure(ServletContext servletContext, EventListener f) {
        // todo need to know what type of listener so it can be proxied
//        ((ProxyListener)proxyListener).setDelegateClass(getClassName());

        try {
            f.getClass().getMethod("setDelegateName",String.class).invoke(f,getClassName());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        servletContext.addListener(f);
    }
}
