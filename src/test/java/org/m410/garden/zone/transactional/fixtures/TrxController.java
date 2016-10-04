package org.m410.garden.zone.transactional.fixtures;

import com.google.common.collect.ImmutableList;
import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.http.Action;
import org.m410.garden.controller.action.http.HttpActionDefinition;

import java.util.List;

import static org.m410.garden.zone.ZoneScope.*;

/**
 * @author m410
 */
public final class TrxController extends Controller {
    private MyService myService;

    public TrxController(MyService myService) {
        super("");
        defaultTransactionScope = Action;
        this.myService = myService;
    }

    @Override
    public List<? extends HttpActionDefinition> actions() {
        return ImmutableList.of(
                get("", noneAction).transaction(None),
                get("service", serviceAction).transaction(None),
                get("action", stdAction),
                get("action-service", actionAndService),
                get("action-view", actionViewAction).transaction(ActionAndView)
        );
    }

    Action noneAction = (request) -> respond().asText("none");

    Action serviceAction = (request) -> respond().asText(myService.list().stream().reduce("",(a,b)->a+" "+b));

    Action stdAction = (request) -> respond().withView("/some/some");

    Action actionAndService = (request) -> respond().asText(myService.list().stream().reduce("",(a,b)->a+" "+b));

    Action actionViewAction = (request) -> respond().withView("/some/view");
}
