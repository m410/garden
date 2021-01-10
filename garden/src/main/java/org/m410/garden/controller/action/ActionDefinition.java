package org.m410.garden.controller.action;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.m410.garden.controller.HttpCtlr;
import org.m410.garden.controller.Securable;
import org.m410.garden.controller.action.http.Action;

import java.util.List;

/**
 * @author m410
 */
public interface ActionDefinition extends Comparable<ActionDefinition>{


    static enum ActionProtocol {
        WS,
        HTTP
    }

    HttpCtlr getController();

    Action getAction();

    PathExpr getPathExpr();

    Securable.Ssl getSsl();

    List<String> getAuthorizedRoles();

    boolean isAuthenticated();

    ActionProtocol getType();

    default int compareTo(ActionDefinition o) {
        return new CompareToBuilder()
                .append(this.getType(), o.getType())
                .append(this.getPathExpr(), o.getPathExpr())
                .toComparison();
    }
}
