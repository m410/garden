package us.m410.j8.sample;


import com.google.common.collect.ImmutableList;
import us.m410.j8.action.Action;
import us.m410.j8.action.ActionDefinition;
import us.m410.j8.action.PathExpr;
import static us.m410.j8.action.Response.*;
import static us.m410.j8.action.direction.Directions.*;
import us.m410.j8.controller.Controller;

import java.util.List;


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
                get("path", myAction, view("/home"))// todo add content-type, roles, ssl
        );
    }

    public final Action myAction = (call) -> {
        final String myEntity = myService.get(call.urlParameters().get("id"));
        return response().withModel("name", myEntity);
    };
}

