package org.m410.garden.controller.action.ws;

import com.google.common.collect.ImmutableList;
import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.ActionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author m410
 */
public class SampleWsController extends Controller {
    static final Logger log = LoggerFactory.getLogger(SampleWsController.class);

    public SampleWsController() {
        super("ws");
    }

    @Override
    public List<? extends ActionDefinition> actions() {
        return ImmutableList.of(
                ws("listen", listen)
        );
    }

    WebSocket listen = socket().message((session)->{
        log.warn("message called, {}",session);
    });
}
