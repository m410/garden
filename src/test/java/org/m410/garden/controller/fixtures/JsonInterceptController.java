package org.m410.garden.controller.fixtures;


import com.google.common.collect.ImmutableList;
import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.PathExpr;
import org.m410.garden.controller.action.http.Action;
import org.m410.garden.controller.action.http.ActionRequest;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.controller.action.http.Response;

import java.util.List;


/**
 */
public final class JsonInterceptController extends Controller {

    @Override
    public Response intercept(ActionRequest actionRequest, Action action) {
        return action.execute(actionRequest).withContentType("text/plain");
    }

    public JsonInterceptController() {
        super(new PathExpr("json"));
    }

    public List<HttpActionDefinition> actions() {
        return ImmutableList.of(
                get("", httpGetAction)
        );
    }

    public final Action httpGetAction = (call) -> {
        final String msg = "<message id=" + call.url().get("id") + "></message>";
        return respond().asJson(msg);
    };

}

