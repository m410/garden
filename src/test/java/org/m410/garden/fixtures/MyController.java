package org.m410.garden.fixtures;


import com.google.common.collect.ImmutableList;
import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.*;
import org.m410.garden.controller.action.http.Action;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.controller.action.http.ActionRequest;
import org.m410.garden.controller.action.http.Response;

import java.util.List;


/**
 * @author Michael Fortin
 */
public final class MyController extends Controller {
    private MyService myService;

    public MyController(MyService myService) {
        super(new PathExpr("home"));
        this.myService = myService;
        this.useSsl = true;
    }

    public List<HttpActionDefinition> actions() {
        return ImmutableList.of(
                get("path", httpGetAction),
                get("path2", httpGetAction).contentTypes("text/html"),
                post("path", httpPostAction),
                put("path", httpPutAction),
                delete("path", httpDeleteAction)
        );
    }


    @Override
    public Response intercept(ActionRequest actionRequest, Action action) throws Exception {
        return super.intercept(actionRequest, action);
    }

    public final Action httpGetAction = (call) -> {
        final String myEntity = myService.get(call.url().get("id"));
        return respond().withModel("name", myEntity).withView("/home");
    };

    public final Action httpPostAction = (call) -> {
        final String myEntity = myService.get(call.url().get("id"));
        return respond().withModel("name", myEntity).withView("/home");
    };

    public final Action httpPutAction = (call) -> {
        final String myEntity = myService.get(call.url().get("id"));
        return respond().withModel("name", myEntity).withView("/home");
    };

    public final Action httpDeleteAction = (call) -> {
        final String myEntity = myService.get(call.url().get("id"));
        return respond().withModel("name", myEntity).withView("/home");
    };
}
