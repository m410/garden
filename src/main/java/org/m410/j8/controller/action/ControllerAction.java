package org.m410.j8.controller.action;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.m410.j8.controller.Ctlr;
import org.m410.j8.controller.Securable;

import java.util.Optional;

/**
 * @author m410
 */
public interface ControllerAction extends Comparable<ControllerAction>{


    static enum ActionProtocol {
        WS,
        HTTP
    }

    Ctlr getController();

    PathExpr getPathExpr();

    Securable.State getSsl();

    Optional<String[]> getAuthorizedRoles();

    boolean isAuthenticated();

    ActionProtocol getType();

    default int compareTo(ControllerAction o) {
        return new CompareToBuilder()
                .append(this.getType(), o.getType())
                .append(this.getPathExpr(), o.getPathExpr())
                .toComparison();
    }
}
