package org.m410.j8.controller;


import com.google.common.collect.ImmutableList;
import org.m410.j8.controller.action.http.Action;
import org.m410.j8.controller.action.http.ActionDefinition;
import org.m410.j8.controller.action.PathExpr;

import java.util.List;


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
        return respond().asXml(msg);
    };

    public final Action httpPostAction = (call) -> {
        return respond().asXml(call.postBodyAsString());
    };

    public final Action httpPutAction = (call) -> {
        return respond().asXml(call.postBodyAsString());
    };

    public final Action httpDeleteAction = (call) -> {
        return respond().asRedirect("/xml");
    };
}

