package us.m410.j8.sample;


import com.google.common.collect.ImmutableList;
import us.m410.j8.action.Action;
import us.m410.j8.action.ActionDefinition;
import us.m410.j8.action.PathExpr;
import us.m410.j8.controller.Controller;

import java.util.List;

import static us.m410.j8.action.Response.response;
import static us.m410.j8.action.direction.Directions.view;


/**
 */
public final class MyController extends Controller {
    private MyService myService;

    public MyController(MyService myService) {
        super(new PathExpr("home"));
        this.myService = myService;
    }

    public List<ActionDefinition> actions() {
        return ImmutableList.of(
                get("path", httpGetAction),
                post("path", httpPostAction),
                put("path", httpPutAction),
                delete("path", httpDeleteAction)
        );
    }

    public final Action httpGetAction = (call) -> {
        final String myEntity = myService.get(call.urlParameters().get("id"));
        return response().withModel("name", myEntity).withView(view("/home"));
    };

    public final Action httpPostAction = (call) -> {
        final String myEntity = myService.get(call.urlParameters().get("id"));
        return response().withModel("name", myEntity).withView(view("/home"));
    };

    public final Action httpPutAction = (call) -> {
        final String myEntity = myService.get(call.urlParameters().get("id"));
        return response().withModel("name", myEntity).withView(view("/home"));
    };

    public final Action httpDeleteAction = (call) -> {
        final String myEntity = myService.get(call.urlParameters().get("id"));
        return response().withModel("name", myEntity).withView(view("/home"));
    };
}

