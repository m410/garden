package org.m410.j8.application;

import com.google.common.collect.ImmutableList;
import org.m410.j8.action.Action;
import org.m410.j8.action.ActionDefinition;
import org.m410.j8.action.PathExpr;
import org.m410.j8.action.Response;
import org.m410.j8.controller.Controller;

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
    public List<ActionDefinition> actions() {
        return ImmutableList.of(
                get("", listAction)
        );
    }

    Action listAction = (req) ->{
        return Response.response();
    };
}
