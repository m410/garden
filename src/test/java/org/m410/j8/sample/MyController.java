package org.m410.j8.sample;


import com.google.common.collect.ImmutableList;
import org.m410.j8.action.*;
import org.m410.j8.controller.Controller;

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

    public List<ActionDefinition> actions() {
        return ImmutableList.of(
                get("path", httpGetAction),
                get("path2", httpGetAction).contentTypes("text/html"),
                post("path", httpPostAction),
                put("path", httpPutAction),
                delete("path", httpDeleteAction)
        );
    }


    @Override
    public Response intercept(ActionRequest actionRequest, Action action) {
        return super.intercept(actionRequest, action);
    }

    public final Action httpGetAction = (call) -> {
        final String myEntity = myService.get(call.urlParameters().get("id"));
        return respond().withModel("name", myEntity).withView("/home");
    };

    public final Action httpPostAction = (call) -> {
        final String myEntity = myService.get(call.urlParameters().get("id"));
        return respond().withModel("name", myEntity).withView("/home");
    };

    public final Action httpPutAction = (call) -> {
        final String myEntity = myService.get(call.urlParameters().get("id"));
        return respond().withModel("name", myEntity).withView("/home");
    };

    public final Action httpDeleteAction = (call) -> {
        final String myEntity = myService.get(call.urlParameters().get("id"));
        return respond().withModel("name", myEntity).withView("/home");
    };
}

