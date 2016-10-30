package org.m410.garden.fixtures;

import com.google.common.collect.ImmutableList;
import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.controller.auth.Authorizable;

import java.util.List;

/**
 * @author m410
 */
public final class MyAuthController extends Controller implements Authorizable {
    private final ImmutableList<String> roles = ImmutableList.of("ADMIN");

    public MyAuthController() {
        super("auth");
        this.contentTypes = new String[]{TEXT_HTML};
    }

    @Override
    public List<String> acceptRoles() {
        return roles;
    }

    @Override
    public List<HttpActionDefinition> actions() {
        return null;
    }
}
