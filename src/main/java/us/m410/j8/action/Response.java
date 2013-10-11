package us.m410.j8.action;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import org.apache.commons.lang3.builder.ToStringBuilder;
import us.m410.j8.action.direction.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class Response implements ActionResponse {
    public static final String XML_CONTENT_TYPE = "application/xml";
    public static final String JSON_CONTENT_TYPE = "application/json";
    public static final String HTML_CONTENT_TYPE = "text/html";
    public static final String PLAIN_CONTENT_TYPE = "text/plain";

    protected Map<String, String> headers = ImmutableSortedMap.of();
    protected Map<String, Object> model = ImmutableSortedMap.of();
    protected Map<String, Object> session = ImmutableSortedMap.of();
    protected Flash flash = null;
    protected Direction direction = Directions.noView();
    protected boolean invalidateSession = false;
    protected String contentType = HTML_CONTENT_TYPE;

    protected ResponseStream responseStream;

    Response(Map<String, String> headers, Map<String, Object> model, Map<String, Object> session,
            Flash flash, Direction viewPath, boolean invalidateSession) {
        this.headers = headers;
        this.model = model;
        this.session = session;
        this.flash = flash;
        this.direction = viewPath;
        this.invalidateSession = invalidateSession;
    }

    Response(Map<String, String> headers, Map<String, Object> model, Map<String, Object> session,
            Flash flash, Direction viewPath, boolean invalidateSession, String contentType) {
        this.headers = headers;
        this.model = model;
        this.session = session;
        this.flash = flash;
        this.direction = viewPath;
        this.invalidateSession = invalidateSession;
        this.contentType = contentType;
    }

    Response(Map<String, String> headers, Map<String, Object> model, Map<String, Object> session,
            Flash flash, Direction viewPath, boolean invalidateSession, String contentType, ResponseStream r) {
        this.headers = headers;
        this.model = model;
        this.session = session;
        this.flash = flash;
        this.direction = viewPath;
        this.invalidateSession = invalidateSession;
        this.contentType = contentType;
        this.responseStream = r;
    }

    public Response() {
    }

    public static Response response() {
        return new Response();
    }

    public Response invalidateSession() {
        return new Response(null, null, null, null, null, true);
    }

    public Response withHeaders(Map<String, String> newHeaders) {
        return new Response(newHeaders, model, session, flash, direction, invalidateSession);
    }

    public Response withHeader(String header, String value) {
        Map<String,String> newHeaders = new HashMap();
        newHeaders.put(header,value);
        Map<String,String> combined = ImmutableMap.<String,String>builder()
                .putAll(headers)
                .putAll(newHeaders)
                .build();
        return new Response(combined, model, session, flash, direction, invalidateSession);
    }

    public Response withModel(Map<String, Object> mod) {
        return new Response(headers, mod, session, flash, direction, invalidateSession);
    }

    public Response withModel(String name, Object value) {
        Map<String, Object> mod = ImmutableSortedMap.of(name, value);
        return new Response(headers, mod, session, flash, direction, invalidateSession);
    }

    public Response withSession(Map<String, Object> sess) {
        return new Response(headers, model, sess, flash, direction, invalidateSession);
    }

    public Response withSession(String name, Object value) {
        Map<String,Object> newSess = new HashMap();
        newSess.put(name, value);
        Map<String,Object> combined = ImmutableMap.<String,Object>builder()
                .putAll(session)
                .putAll(newSess)
                .build();
        return new Response(headers, model, combined, flash, direction, invalidateSession);
    }

    public Response withView(Direction direct) {
        return new Response(headers, model, session, flash, direct, invalidateSession);
    }

    public Response withFlash(String flash) {
        return new Response(headers, model, session, new FlashImpl(flash), direction, invalidateSession);
    }

    public Response asText(String v) {
        Map<String, String> headers = ImmutableSortedMap.of("content-type", PLAIN_CONTENT_TYPE);
        ResponseStream s = (outputStream)->{
            try {
                outputStream.write(v.getBytes());
            }
            catch (IOException e) {
                throw new RuntimeIOException(e);
            }
        };
        return new Response(headers, model, session, flash, Directions.noView(), invalidateSession, PLAIN_CONTENT_TYPE,s);
    }

    public Response asJson(String v) {
        Map<String, String> headers = ImmutableSortedMap.of("content-type", JSON_CONTENT_TYPE);
        ResponseStream s = (outputStream)->{
            try {
                outputStream.write(v.getBytes());
            }
            catch (IOException e) {
                throw new RuntimeIOException(e);
            }
        };
        return new Response(headers, model, session, flash, Directions.noView(), invalidateSession,JSON_CONTENT_TYPE,s);
    }

    public Response asXml(String v) {
        Map<String, String> headers = ImmutableSortedMap.of("content-type", XML_CONTENT_TYPE);
        ResponseStream s = (outputStream)->{
            try {
                outputStream.write(v.getBytes());
            }
            catch (IOException e) {
                throw new RuntimeIOException(e);
            }
        };
        return new Response(headers, model, session, flash, Directions.noView(), invalidateSession, XML_CONTENT_TYPE,s);
    }

    public Response contentType(String s) {
        return new Response(headers, model, session, flash, direction, invalidateSession, s);
    }

    public Response stream(ResponseStream s) {
        return new Response(headers, model, session, flash, direction, invalidateSession, contentType,s);
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public Map<String, Object> getModel() {
        return model;
    }

    @Override
    public Map<String, Object> getSession() {
        return session;
    }

    @Override
    public Flash getFlash() {
        return flash;
    }

    @Override
    public boolean doInvalidateSession() {
        return invalidateSession;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void handleResponse(HttpServletRequest request, HttpServletResponse response) {
        if(responseStream != null) {
            try {
                response.setContentType(contentType);
                responseStream.stream(response.getOutputStream());
            }
            catch (IOException e) {
                throw new RuntimeIOException(e);
            }
        }
        else if(invalidateSession && request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
        else {
            model.forEach(request::setAttribute);
            session.forEach(request.getSession()::setAttribute);
            headers.forEach(response::addHeader);

            if(flash != null)
                request.getSession().setAttribute("flash", flash.forSession(request.getSession()));

            switch(direction.id()) {
                case Direction.VIEW:
                    try {
                        request.getRequestDispatcher(((View)direction).getPath()).forward(request,response);
                    }
                    catch (ServletException|IOException e) {
                        throw new RuntimeIOException(e);
                    }
                    break;
                case Direction.REDIRECT:
                    try {
                        response.sendRedirect(((Redirect)direction).getPath());
                    }
                    catch (IOException e) {
                        throw new RuntimeIOException(e);
                    }
                case Direction.NO_VIEW:
                    break;
            }
        }

    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("headers", headers)
                .append("model", model)
                .append("session", session)
                .append("flash", flash)
                .append("viewPath", direction)
                .append("invalidateSession", invalidateSession)
                .toString();
    }
}
