package org.m410.angular.web;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import org.m410.angular.model.person.Person;
import org.m410.angular.model.person.PersonService;

import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.http.Action;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.module.auth.User;

import java.util.List;
import java.util.Optional;

/**
 * @author m410
 */
public class AuthorizeController extends Controller implements Mapped {
    private PersonService personService;
    private final Gson gson = new Gson();

    public AuthorizeController(PersonService personService) {
        super("/authorize");
        this.personService = personService;
    }

    @Override
    public List<? extends HttpActionDefinition> actions() {
        return ImmutableList.of(
                post("", doAuth).contentTypes("application/json")
        );
    }

    Action doAuth = req -> {
        final Optional<User> u = personService.authorize(req.params());
        if(u.isPresent())
            return respond().asJson(gson.toJson(new Auth(personService.findBy(u.get().getUserName()))));
        else
            return respond().asJson(gson.toJson(new Auth()));
    };

    class Auth {
        private boolean success = false;
        private String fullName;
        private String userName;
        private String[] roles;
        private Long id;

        public Auth() { }

        public Auth(Person p) {
            this.success = true;
            this.fullName = p.getFirstName() + " " + p.getLastName();
            this.userName = p.getUserName();
            this.id = p.getId();
            this.roles = p.getRoles().toArray(new String[p.getRoles().size()]);
        }

        boolean isSuccess() { return success; }
        String getFullName() { return fullName; }
        String getUserName() { return userName; }
        String[] getRoles() { return roles; }
        Long getId() { return id; }
    }
}
