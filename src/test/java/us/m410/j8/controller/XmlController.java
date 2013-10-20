package us.m410.j8.controller;


import com.google.common.collect.ImmutableList;
import us.m410.j8.action.Action;
import us.m410.j8.action.ActionDefinition;
import us.m410.j8.action.PathExpr;
import us.m410.j8.action.direction.Redirect;

import java.util.List;

import static us.m410.j8.action.Response.response;


/**
 */
public final class XmlController extends Controller {

    public XmlController() {
        super(new PathExpr("xml"));
    }

    public List<ActionDefinition> actions() {
        return ImmutableList.of(
                get("", httpGetAction),
                post("", httpPostAction),
                put("{id:\\d+}", httpPutAction),
                delete("{id:\\d+}", httpDeleteAction)
        );
    }

    public final Action httpGetAction = (call) -> {
        System.out.println("HTTP GET ACTION");
        final String msg = "<message id=" + call.urlParameters().get("id") + "></message>";
        return response().asXml(msg);
    };

    public final Action httpPostAction = (call) -> {
        System.out.println("HTTP POST ACTION");
        return response().asXml(call.postBodyAsString());
    };

    public final Action httpPutAction = (call) -> {
        System.out.println("HTTP PUT ACTION");
        return response().asXml(call.postBodyAsString());
    };

    public final Action httpDeleteAction = (call) -> {
        System.out.println("HTTP DELETE ACTION");
        return response().withView(Redirect.to("/xml"));
    };
}

