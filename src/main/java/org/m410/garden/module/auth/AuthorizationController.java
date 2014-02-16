package org.m410.garden.module.auth;

import com.google.common.collect.ImmutableList;
import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.ActionDefinition;
import org.m410.garden.controller.action.http.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author m410
 */
public class AuthorizationController extends Controller {
    static final Logger log = LoggerFactory.getLogger(AuthorizationController.class);
    protected AuthorizationProvider authorizationProvider;
    protected String formView = "/authorize/index.jsp";
    protected String logoutView = "/authorize/index.jsp";
    protected String invalidMessage = "Your user name or password was incorrect";

    public AuthorizationController(AuthorizationProvider authorizationProvider) {
        super(authorizationProvider.loginBaseUri());
        this.authorizationProvider = authorizationProvider;
    }

    @Override
    public List<? extends ActionDefinition> actions() {
        return ImmutableList.of(
                post("", authAction),
                get("logout", deauthAction)
        );
    }

    Action authAction = req -> {
        log.info("req:{}",req);
        Optional<User> authorized = authorizationProvider.authorize(toMap(req.requestParameters()));
        log.info("login:{}",authorized);

        if(authorized.isPresent())
            return respond().withSession(AuthorizationProvider.SESSION_KEY,authorized.get())
                    .asRedirect(authorizationProvider.successUri());
        else
            return respond().withModel("invalidMessage",invalidMessage)
                    .withView(formView);
    };

    Action deauthAction = req -> respond().invalidateSession().withView(logoutView);

    protected Map<String,String> toMap(Map<String,String[]> in) {
        Map<String,String> out = new HashMap<>();
        in.forEach((k,v)->out.put(k,v[0]));
        return out;
    }
}
