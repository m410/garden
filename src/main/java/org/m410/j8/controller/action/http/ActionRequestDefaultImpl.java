package org.m410.j8.controller.action.http;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.IOUtils;
import org.m410.j8.controller.action.NotAPostException;
import org.m410.j8.controller.action.PathExpr;
import org.m410.j8.controller.action.UserPrincipal;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ActionRequestDefaultImpl implements ActionRequest {

    private HttpServletRequest servletRequest;
    private PathExpr pathExpr;

    private ActionRequestDefaultImpl() {
    }

    public ActionRequestDefaultImpl(HttpServletRequest servletRequest, PathExpr pathExpr) {
        this.servletRequest = servletRequest;
        this.pathExpr = pathExpr;
    }

    @Override
    public boolean isActiveSession() {
        return servletRequest.getSession(false) != null;
    }

    @Override
    public UserPrincipal userPrincipal() {
        return new UserPrincipal(servletRequest);
    }

    @Override
    public Map<String, Object> session() {

        if(isActiveSession()) {
            HashMap<String,Object> map = new HashMap<>();
            Enumeration<String> attributeNames = servletRequest.getSession(false).getAttributeNames();

            while (attributeNames.hasMoreElements()) {
                String name = attributeNames.nextElement();
                map.put(name,servletRequest.getSession(false).getAttribute(name));
            }

            return ImmutableMap.copyOf(map);
        }
        else {
            return ImmutableMap.of();
        }
    }

    @Override
    public RequestProperties requestProperties() {
        return new RequestProperties(servletRequest);
    }

    @Override
    public Map<String, String> requestHeaders() {
        HashMap<String,String> map = new HashMap<>();
        Enumeration<String> headerNames = servletRequest.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            map.put(name, servletRequest.getHeader(name));
        }

        return ImmutableMap.copyOf(map);
    }

    @Override
    public Map<String, String> urlParameters() {
        return pathExpr.parametersForRequest(servletRequest);
    }

    @Override
    public Map<String, String[]> requestParameters() {
        return ImmutableMap.copyOf(servletRequest.getParameterMap());
    }

    @Override
    public InputStream postBodyAsStream() {
        try {
            return servletRequest.getInputStream();
        }
        catch (IOException e) {
            throw new NotAPostException(e);
        }
    }

    @Override
    public String postBodyAsString() {
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(servletRequest.getInputStream(), writer, "UTF-8");
            return writer.toString();
        }
        catch (IOException e) {
            throw new NotAPostException(e);
        }
    }
}
