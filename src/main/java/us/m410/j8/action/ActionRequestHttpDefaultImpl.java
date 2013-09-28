package us.m410.j8.action;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Map;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class ActionRequestHttpDefaultImpl implements ActionRequest {

    private HttpServletRequest servletRequest;

    private ActionRequestHttpDefaultImpl() {
    }

    public ActionRequestHttpDefaultImpl(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    @Override
    public boolean isActiveSession() {
        return servletRequest.getSession(false) == null;
    }

    @Override
    public UserPrincipal userPrincipal() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, Object> session() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, String> requestProperties() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, String> requestHeaders() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, String> urlParameters() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, String[]> requestParameters() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public InputStream postBodyAsStream() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String postBodyAsString() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
