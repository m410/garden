package org.m410.j8.controller;


import com.google.common.collect.ImmutableList;
import org.m410.j8.action.Action;
import org.m410.j8.action.ActionDefinition;
import org.m410.j8.action.PathExpr;
import org.m410.j8.action.direction.Redirect;

import java.util.List;

import static org.m410.j8.action.Response.response;


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
        final String msg = "<message id=" + call.urlParameters().get("id") + "></message>";
        return response().asXml(msg);
    };

    public final Action httpPostAction = (call) -> {
        return response().asXml(call.postBodyAsString());
    };

    public final Action httpPutAction = (call) -> {
        return response().asXml(call.postBodyAsString());
    };

    public final Action httpDeleteAction = (call) -> {
        return response().withView(Redirect.to("/xml"));
    };
}

