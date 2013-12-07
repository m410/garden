package org.m410.j8.controller;


import com.google.common.collect.ImmutableList;
import org.m410.j8.action.Action;
import org.m410.j8.action.ActionDefinition;
import org.m410.j8.action.PathExpr;

import java.util.List;


/**
 */
public final class JsonController extends Controller {

    public JsonController() {
        super(new PathExpr("json"));
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
        return respond().asJson(msg);
    };

    public final Action httpPostAction = (call) -> {
        return respond().asJson(call.postBodyAsString());
    };

    public final Action httpPutAction = (call) -> {
        return respond().asJson(call.postBodyAsString());
    };

    public final Action httpDeleteAction = (call) -> {
        return respond().redirect("/xml");
    };
}

