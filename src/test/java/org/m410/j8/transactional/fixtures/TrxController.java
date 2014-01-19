package org.m410.j8.transactional.fixtures;

import com.google.common.collect.ImmutableList;
import org.m410.j8.controller.Controller;
import org.m410.j8.controller.action.ActionDefinition;
import org.m410.j8.controller.action.http.Action;

import java.util.List;

import static org.m410.j8.transaction.TransactionScope.*;

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
    public List<? extends ActionDefinition> actions() {
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
