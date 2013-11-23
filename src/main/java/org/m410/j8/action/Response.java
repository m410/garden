package org.m410.j8.action;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.m410.j8.action.direction.*;

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

    /**
     * Create an instance with default values
     */
    public Response() {
    }

    /**
     *  A new Response.
     *  @return a new Response.
     */
    public static Response response() {
        return new Response();
    }

    /**
     * creates a new instance that invalidates all values.
     * @return a new Response.
     */
    public Response invalidateSession() {
        return new Response(null, null, null, null, null, true);
    }

    /**
     * Add a map of header values to the response
     * @param newHeaders the map of headers
     * @return a new Response.
     */
    public Response withHeaders(Map<String, String> newHeaders) {
        return new Response(newHeaders, model, session, flash, direction, invalidateSession);
    }

    /**
     * Add a single header name value pair to the response.
     * @param header the name of the header
     * @param value the value of the header
     * @return a new Response
     */
    public Response withHeader(String header, String value) {
        Map<String,String> newHeaders = new HashMap();
        newHeaders.put(header,value);
        Map<String,String> combined = ImmutableMap.<String,String>builder()
                .putAll(headers)
                .putAll(newHeaders)
                .build();
        return new Response(combined, model, session, flash, direction, invalidateSession);
    }

    /**
     * Add a map of parameters to the request to be used in views as the model.
     * @param mod the map of model objects.
     * @return a new Response.
     */
    public Response withModel(Map<String, Object> mod) {
        return new Response(headers, mod, session, flash, direction, invalidateSession);
    }

    /**
     * Add a single model name value pair the request object.
     * @param name the name of the model object.
     * @param value the value of the model.
     * @return a new Response object.
     */
    public Response withModel(String name, Object value) {
        Map<String, Object> mod = ImmutableSortedMap.of(name, value);
        return new Response(headers, mod, session, flash, direction, invalidateSession);
    }

    /**
     * Add a map of attributes to add the the session object.
     * @param sess map of name value pairs.
     * @return a new Response.
     */
    public Response withSession(Map<String, Object> sess) {
        return new Response(headers, model, sess, flash, direction, invalidateSession);
    }

    /**
     * add a single name value pair to the session object.
     * @param name the name of object.
     * @param value the value.
     * @return a new Response.
     */
    public Response withSession(String name, Object value) {
        Map<String,Object> newSess = new HashMap();
        newSess.put(name, value);
        Map<String,Object> combined = ImmutableMap.<String,Object>builder()
                .putAll(session)
                .putAll(newSess)
                .build();
        return new Response(headers, model, combined, flash, direction, invalidateSession);
    }

    /**
     * The full context relative path of the view to display for this action if any.  This will
     * typically be response().withModel("name","value").withView("/person/view.jsp");
     * @param v the path of the view
     * @return a new Response.
     */
    public Response withView(String v) {
        return new Response(headers, model, session, flash, new View(v), invalidateSession);
    }

    /**
     * Can be either context relative or absolute path to redirect too.
     * @param v the path, eg. "/index.jsp" or "http://somesite.com/page"
     * @return a new Response
     */
    public Response redirect(String v) {
        return new Response(headers, model, session, flash, new Redirect(v), invalidateSession);
    }

    /**
     * Internal forwarding of the request within the application.
     * @param v the context relative path.
     * @return a new Response.
     */
    public Response forward(String v) {
        return new Response(headers, model, session, flash, new Forward(v), invalidateSession);
    }

    /**
     * Add a flash object to the response
     * @todo needs flash message types and internationalization.
     * @param flash the content of the flash message.
     * @return a new Response.
     */
    public Response withFlash(String flash) {
        return new Response(headers, model, session, new FlashImpl(flash), direction, invalidateSession);
    }

    /**
     * Sets the response as plain text and returns the value directly to the client.  If this
     * is used with the withView() method, this will take precedence and the view will be ignored.
     * @param v the content to return
     * @return a new response.
     */
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

    /**
     * Sets the response as json text and returns the value directly to the client.  If this
     * is used with the withView() method, this will take precedence and the view will be ignored.
     * @param v the content to return
     * @return a new response.
     */
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

    /**
     * Sets the response as xml text content type and returns the value directly to the client.  If this
     * is used with the withView() method, this will take precedence and the view will be ignored.
     * @param v the content to return
     * @return a new response.
     */
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

    /**
     * Explicitly sets the content type of the response.  Typically used in conjunction with the
     * stream response method.
     * @param s the content type
     * @return a new Response
     */
    public Response contentType(String s) {
        return new Response(headers, model, session, flash, direction, invalidateSession, s);
    }

    /**
     * Takes a closure as an argument to stream content to the client.
     *
     * <code>
     *     response().contentType("text/json").stream(out->out.write(""));
     * </code>
     * @param s response stream
     * @return a new Response object
     */
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

    /**
     * This is called by container to execute the response.
     * @param request the servlet request.
     * @param response the servlet response.
     */
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
