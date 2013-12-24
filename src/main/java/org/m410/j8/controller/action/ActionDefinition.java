package org.m410.j8.controller.action;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.m410.j8.controller.Ctlr;
import org.m410.j8.controller.Securable;

import java.util.List;
import java.util.Optional;

/**
 * @author m410
 */
public interface ActionDefinition extends Comparable<ActionDefinition>{


    static enum ActionProtocol {
        WS,
        HTTP
    }

    Ctlr getController();

    PathExpr getPathExpr();

    Securable.State getSsl();

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
