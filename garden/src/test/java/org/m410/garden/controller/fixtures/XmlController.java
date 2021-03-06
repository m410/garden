package org.m410.garden.controller.fixtures;


import com.google.common.collect.ImmutableList;
import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.http.Action;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.controller.action.PathExpr;

import java.util.List;


/**
 */
public final class XmlController extends Controller {

    public XmlController() {
        super(new PathExpr("xml"));
    }

    public List<HttpActionDefinition> actions() {
        return ImmutableList.of(
                get("", httpGetAction),
                post("", httpPostAction),
                put("{id:\\d+}", httpPutAction),
                delete("{id:\\d+}", httpDeleteAction)
        );
    }

    public final Action httpGetAction = (call) -> {
        final String msg = "<message id=" + call.url().get("id") + "></message>";
        return respond().asXml(msg);
    };

    public final Action httpPostAction = (call) -> {
        return respond().asXml(call.bodyAsString());
    };

    public final Action httpPutAction = (call) -> {
        return respond().asXml(call.bodyAsString());
    };

    public final Action httpDeleteAction = (call) -> {
        return respond().asRedirect("/xml");
    };
}

