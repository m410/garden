package org.m410.garden.module.auth;

import com.google.common.collect.ImmutableList;
import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.ActionDefinition;
import org.m410.garden.controller.action.http.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author m410
 */
public class AuthorizationController extends Controller {

    protected AuthorizationProvider authorizationProvider;
    protected String formView = "/authorize/index.jsp";
    protected String logoutView = "/authorize/index.jsp";
    protected String successView = "/home";
    protected String invalidMessage = "Your user name or password was incorrect";

    public AuthorizationController(AuthorizationProvider authorizationProvider) {
        super("/authorize");
        this.authorizationProvider = authorizationProvider;
    }

    @Override
    public List<? extends ActionDefinition> actions() {
        return ImmutableList.of(
                get("", authAction),
                get("logout", deauthAction)
        );
    }

    Action authAction = req -> {
        Optional<User> authorized = authorizationProvider.authorize(toMap(req.requestParameters()));

        if(authorized.isPresent())
            return respond().withSession("authorized_user",authorized.get()).withView(successView);
        else
            return respond().withView(formView).withModel("invalidMessage",invalidMessage);
    };

    Action deauthAction = req -> respond().invalidateSession().withView(logoutView);

    protected Map<String,String> toMap(Map<String,String[]> in) {
        Map<String,String> out = new HashMap<>();
        in.forEach((k,v)->out.put(k,v[0]));
        return out;
    }
}
