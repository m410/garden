package us.m410.j8.action;

import com.google.common.collect.ImmutableSortedMap;
import org.apache.commons.lang3.builder.ToStringBuilder;
import us.m410.j8.action.direction.Direction;
import us.m410.j8.action.direction.NoView;

import java.util.Map;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class Response implements ActionResponse {
    protected Map<String, String> headers = ImmutableSortedMap.of();
    protected Map<String, Object> model = ImmutableSortedMap.of();
    protected Map<String, Object> session = ImmutableSortedMap.of();
    protected Flash flash = null;
    protected Direction direction = null;
    protected boolean invalidateSession = false;

    Response(Map<String, String> headers, Map<String, Object> model, Map<String, Object> session, Flash flash, Direction viewPath, boolean invalidateSession) {
        this.headers = headers;
        this.model = model;
        this.session = session;
        this.flash = flash;
        this.direction = viewPath;
        this.invalidateSession = invalidateSession;
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

    public Response withView(Direction direct) {
        return new Response(headers, model, session, flash, direct, invalidateSession);
    }

    public Response withFlash(Flash flsh) {
        return new Response(headers, model, session, flsh, direction, invalidateSession);
    }

    public Response asText(String v) {
        Map<String, String> headers = ImmutableSortedMap.of("content-type", "text/plain");
        return new Response(headers, model, session, flash, new NoView(), invalidateSession);
    }

    public Response asJson(String v) {
        Map<String, String> headers = ImmutableSortedMap.of("content-type", "text/json");
        return new Response(headers, model, session, flash, new NoView(), invalidateSession);
    }

    public Response asXml(String v) {
        Map<String, String> headers = ImmutableSortedMap.of("content-type", "text/xml");
        return new Response(headers, model, session, flash, new NoView(), invalidateSession);
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
