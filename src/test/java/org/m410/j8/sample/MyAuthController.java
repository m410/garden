package org.m410.j8.sample;

import com.google.common.collect.ImmutableList;
import org.m410.j8.controller.action.http.ActionDefinition;
import org.m410.j8.controller.Authorizable;
import org.m410.j8.controller.Controller;

import java.util.List;

/**
 * @author m410
 */
public class MyAuthController extends Controller implements Authorizable {
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
    public List<ActionDefinition> actions() {
        return null;
    }
}
