package org.m410.j8.action;

import java.io.InputStream;
import java.util.Map;

/**
 */
public interface ActionRequest {

    boolean isActiveSession();

    UserPrincipal userPrincipal();

    Map<String, Object> session();

    RequestProperties requestProperties();

    Map<String, String> requestHeaders();

    Map<String, String> urlParameters();

    Map<String, String[]> requestParameters();

    InputStream postBodyAsStream();

    String postBodyAsString();
}
