package org.m410.j8.controller.action.http;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.m410.j8.controller.action.flash.Flash;
import org.m410.j8.controller.action.flash.FlashImpl;
import org.m410.j8.controller.action.NotAPostException;
import org.m410.j8.controller.action.http.direction.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * All actions must return an instance of this Response object.  This implements
 * the immutable builder design patterns.
 *
 * @author Michael Fortin
 */
public final class Response {
    public static final String CONTENT_TYPE_XML = "application/xml";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_HTML = "text/html";
    public static final String CONTENT_TYPE_PLAIN = "text/plain";

    //  https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
    public static final int SUCCESS_OK = 200;
    public static final int ERROR_FORBIDDEN = 403;
    public static final int REDIRECT_SEE_OTHER = 303;

    private final Map<String, String> headers;
    private final Map<String, Object> model;
    private final Map<String, Object> session;

    private final Flash flash;

    private final Direction direction;
    private final boolean invalidateSession;
    private final String contentType;

    private final ResponseStream responseStream;

    private final int status;


    Response(Map<String, String> headers, Map<String, Object> model, Map<String, Object> session,
            Flash flash, Direction viewPath, boolean invalidateSession, int status) {
        this.headers = headers;
        this.model = model;
        this.session = session;
        this.flash = flash;
        this.direction = viewPath;
        this.invalidateSession = invalidateSession;
        this.contentType = CONTENT_TYPE_HTML;
        this.responseStream = null;
        this.status = status;
    }

    Response(Map<String, String> headers, Map<String, Object> model, Map<String, Object> session,
            Flash flash, Direction viewPath, boolean invalidateSession, String contentType, int status) {
        this.headers = headers;
        this.model = model;
        this.session = session;
        this.flash = flash;
        this.direction = viewPath;
        this.invalidateSession = invalidateSession;
        this.contentType = contentType;
        this.responseStream = null;
        this.status = status;

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
        this.status = SUCCESS_OK;
    }

    /**
     * Create an instance with default values
     */
    public Response() {
        this.headers = ImmutableSortedMap.of();
        this.model = ImmutableSortedMap.of();
        this.session = ImmutableSortedMap.of();
        this.flash = null;
        this.direction = Directions.noView();
        this.invalidateSession = false;
        this.contentType = CONTENT_TYPE_HTML;
        this.responseStream = null;
        this.status = SUCCESS_OK;
    }

    /**
     *  A new Response.
     *  @return a new Response.
     */
    public static Response respond() {
        return new Response();
    }

    /**
     * creates a new instance that invalidates all values.
     * @return a new Response.
     */
    public Response invalidateSession() {
        return new Response(null, null, null, null, null, true, status);
    }

    /**
     * Add a map of header values to the response
     * @param newHeaders the map of headers
     * @return a new Response.
     */
    public Response withHeaders(Map<String, String> newHeaders) {
        return new Response(newHeaders, model, session, flash, direction, invalidateSession, status);
    }

    /**
     * Add a single header name value pair to the response.
     * @param header the name of the header
     * @param value the value of the header
     * @return a new Response
     */
    public Response withHeader(String header, String value) {
        Map<String,String> newHeaders = new HashMap<>();
        newHeaders.put(header,value);
        Map<String,String> combined = ImmutableMap.<String,String>builder()
                .putAll(headers)
                .putAll(newHeaders)
                .build();
        return new Response(combined, model, session, flash, direction, invalidateSession, status);
    }

    /**
     * Add a map of parameters to the request to be used in views as the model.
     * @param mod the map of model objects.
     * @return a new Response.
     */
    public Response withModel(Map<String, Object> mod) {
        return new Response(headers, mod, session, flash, direction, invalidateSession, status);
    }

    public Response withStatus(int status) {
        return new Response(headers, model, session, flash, direction, invalidateSession, status);
    }

    /**
     * Add a single model name value pair the request object.
     * @param name the name of the model object.
     * @param value the value of the model.
     * @return a new Response object.
     */
    public Response withModel(String name, Object value) {
        Map<String, Object> mod = ImmutableSortedMap.of(name, value);
        return new Response(headers, mod, session, flash, direction, invalidateSession, status);
    }

    /**
     * Add a map of attributes to add the the session object.
     * @param sess map of name value pairs.
     * @return a new Response.
     */
    public Response withSession(Map<String, Object> sess) {
        return new Response(headers, model, sess, flash, direction, invalidateSession, status);
    }

    /**
     * add a single name value pair to the session object.
     * @param name the name of object.
     * @param value the value.
     * @return a new Response.
     */
    public Response withSession(String name, Object value) {
        Map<String,Object> newSess = new HashMap<>();
        newSess.put(name, value);
        Map<String,Object> combined = ImmutableMap.<String,Object>builder()
                .putAll(session)
                .putAll(newSess)
                .build();
        return new Response(headers, model, combined, flash, direction, invalidateSession, status);
    }

    /**
     * The full context relative path of the view to display for this action if any.  This will
     * typically be respond().withModel("name","value").withView("/person/view.jsp");
     *
     * @param v the path of the view
     * @return a new Response.
     */
    public Response withView(String v) {
        return new Response(headers, model, session, flash, new View(v), invalidateSession, status);
    }

    /**
     * Can be either context relative or absolute path to redirect too.
     *
     * @param v the path, eg. "/index.jsp" or "http://somesite.com/page"
     * @return a new Response
     */
    public Response asRedirect(String v) {
        return new Response(headers, model, session, flash, new Redirect(v), invalidateSession, REDIRECT_SEE_OTHER);
    }

    /**
     * Internal forwarding of the request within the application.
     *
     * @param v the context relative path.
     * @return a new Response.
     */
    public Response forward(String v) {
        return new Response(headers, model, session, flash, new Forward(v), invalidateSession, status);
    }

    /**
     * Add a flash object to the response
     *
     * @param flash the content of the flash message.
     * @return a new Response.
     */
    public Response withFlash(String flash) {
        return new Response(headers, model, session, new FlashImpl(flash), direction, invalidateSession, status);
    }

    /**
     * An internationalized version of a flash message.  It takes a default value and the i18n
     * properties key as arguments.
     *
     * @param flash default message
     * @param i18nKey property key
     * @return a new Response
     */
    public Response withFlash(String flash, String i18nKey) {
        return new Response(headers, model, session, new FlashImpl(flash, i18nKey), direction, invalidateSession, status);
    }

    /**
     * Sets the response as plain text and returns the value directly to the client.  If this
     * is used with the withView() method, this will take precedence and the view will be ignored.
     *
     * @param v the content to return
     * @return a new response.
     */
    public Response asText(String v) {
        Map<String, String> headers = ImmutableSortedMap.of("content-type", CONTENT_TYPE_PLAIN);
        ResponseStream s = (outputStream)->{
            try {
                outputStream.write(v.getBytes());
            }
            catch (IOException e) {
                throw new NotAPostException(e);
            }
        };
        return new Response(headers, model, session, flash, Directions.noView(), invalidateSession, CONTENT_TYPE_PLAIN,s);
    }

    /**
     * Sets the response as json text and returns the value directly to the client.  If this
     * is used with the withView() method, this will take precedence and the view will be ignored.
     *
     * @param v the content to return
     * @return a new response.
     */
    public Response asJson(String v) {
        Map<String, String> headers = ImmutableSortedMap.of("content-type", CONTENT_TYPE_JSON);
        ResponseStream s = (outputStream)->{
            try {
                outputStream.write(v.getBytes());
            }
            catch (IOException e) {
                throw new NotAPostException(e);
            }
        };
        return new Response(headers, model, session, flash, Directions.noView(), invalidateSession, CONTENT_TYPE_JSON,s);
    }

    /**
     * Sets the response as xml text content type and returns the value directly to the client.  If this
     * is used with the withView() method, this will take precedence and the view will be ignored.
     *
     * @param v the content to return
     * @return a new response.
     */
    public Response asXml(String v) {
        Map<String, String> headers = ImmutableSortedMap.of("content-type", CONTENT_TYPE_XML);
        ResponseStream s = (outputStream)->{
            try {
                outputStream.write(v.getBytes());
            }
            catch (IOException e) {
                throw new NotAPostException(e);
            }
        };
        return new Response(headers, model, session, flash, Directions.noView(), invalidateSession, CONTENT_TYPE_XML,s);
    }

    /**
     * Explicitly sets the content type of the response.  Typically used in conjunction with the
     * stream response method.
     *
     * @param s the content type
     * @return a new Response
     */
    public Response withContentType(String s) {
        return new Response(headers, model, session, flash, direction, invalidateSession, s, status);
    }

    /**
     * Takes a closure as an argument to stream content to the client.
     *
     * <pre>
     *     respond().contentType("text/json").stream(out-&gt;out.write(""));
     * </pre>
     *
     * @param s response stream
     * @return a new Response object
     */
    public Response asStream(ResponseStream s) {
        return new Response(headers, model, session, flash, direction, invalidateSession, contentType,s);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Direction getDirection() {
        return direction;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public Flash getFlash() {
        return flash;
    }

    public boolean doInvalidateSession() {
        return invalidateSession;
    }

    public String getContentType() {
        return contentType;
    }

    /**
     * This is called by container to execute the response.
     * @param request the servlet request.
     * @param response the servlet response.
     */
    public void handleResponse(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(status);
        response.setContentType(contentType);

        if(responseStream != null) {
            try {
                responseStream.stream(response.getOutputStream());
            }
            catch (IOException e) {
                throw new NotAPostException(e);
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
                        break;
                    }
                    catch (ServletException|IOException e) {
                        throw new NotAPostException(e);
                    }
                case Direction.REDIRECT:
                    try {
                        response.sendRedirect(((Redirect)direction).getPath());
                        break;
                    }
                    catch (IOException e) {
                        throw new NotAPostException(e);
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
