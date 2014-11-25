package org.m410.garden.controller.auth;

import com.google.common.collect.ImmutableList;
import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.http.Action;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author m410
 */
// todo fix me, this should be abstract
public class AuthenticationController extends Controller {
    static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    protected AuthenticationProvider<User> authenticationProvider;
    protected String formView = "/authorize/index.jsp";
    protected String logoutView = "/authorize/index.jsp";
    protected String invalidMessage = "Your user name or password was incorrect";

    public AuthenticationController(AuthenticationProvider<User> authenticationProvider) {
        super(authenticationProvider.loginBaseUri());
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public List<? extends HttpActionDefinition> actions() {
        return ImmutableList.of(
                post("", authAction),
                get("logout", deauthAction)
        );
    }

    Action authAction = req -> {
        log.info("req:{}",req);
        AuthorizationStatus<User> authorized = authenticationProvider.authorize(toMap(req.request()));
        log.info("login:{}",authorized);

        if(authorized.isAuthorized())
            return respond().withSession(AuthenticationProvider.SESSION_KEY,authorized.get())
                    .asRedirect(authenticationProvider.successUri());
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
