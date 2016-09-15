package org.m410.garden.application;

import com.google.common.collect.ImmutableList;
import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.http.Action;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.controller.action.PathExpr;
import org.m410.garden.controller.action.http.Response;

import java.util.List;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class MockController extends Controller {
    public MockController() {
        super(new PathExpr("mock"));
    }

    @Override
    public List<HttpActionDefinition> actions() {
        return ImmutableList.of(
                get("", listAction)
        );
    }

    Action listAction = (req) ->{
        return Response.respond();
    };
}
