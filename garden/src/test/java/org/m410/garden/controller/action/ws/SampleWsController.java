package org.m410.garden.controller.action.ws;

import com.google.common.collect.ImmutableList;
import org.m410.garden.controller.WsController;
import org.m410.garden.controller.action.ActionDefinition;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author m410
 */
public class SampleWsController extends WsController {
    static final Logger log = LoggerFactory.getLogger(SampleWsController.class);

    public SampleWsController() {
        super("ws");
    }

    public List<? extends HttpActionDefinition> actions() {
        return ImmutableList.of(
//                ws("listen", listen)
        );
    }

    WebSocket listen = socket().message((session)->{
        log.warn("message called, {}",session);
    });
}
