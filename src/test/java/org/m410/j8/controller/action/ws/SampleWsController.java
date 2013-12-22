package org.m410.j8.controller.action.ws;

import com.google.common.collect.ImmutableList;
import org.m410.j8.controller.Controller;
import org.m410.j8.controller.action.http.ActionDefinition;

import java.util.List;

/**
 * @author m410
 */
public class SampleWsController extends Controller {

    public SampleWsController() {
        super("ws");
    }

    @Override
    public List<ActionDefinition> actions() {
        return ImmutableList.of(
//                ws("listen", listen)
        );
    }

    WebSocket listen = socket().message((session)->{

    });
}
