package org.m410.angular.web;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.m410.angular.model.address.Address;
import org.m410.angular.model.person.Person;
import org.m410.angular.model.person.PersonService;
import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.http.Action;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Basic restful controller.
 *
 * @author Michael Fortin
 */
public final class PersonController extends Controller {
    private final Logger log = LoggerFactory.getLogger(PersonController.class);
    private PersonService personService;

    private final Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {

                public boolean shouldSkipClass(Class<?> clazz) {
                    return (clazz == Address.class);
                }

                public boolean shouldSkipField(FieldAttributes f) {
                    log.debug("name:{}",f.getName());
                    return f.getName().toLowerCase().contains("address");
                }

            }).create();

    public PersonController(PersonService service) {
        super("persons");
        this.personService = service;
    }

    @Override public List<? extends HttpActionDefinition> actions() {
        return ImmutableList.of(
                get("", list),
                post("", save),
                get("create", create),
                get("{id:\\d+}", view),
                put("{id:\\d+}", update),
                delete("{id:\\d+}", delete)
        );
    }

    Action list = req -> respond().asJson(gson.toJson(personService.list()));

    Action create = req -> respond().asJson(gson.toJson(new Person()));

    Action save = request -> {
        personService.save(gson.fromJson(request.bodyAsString(), Person.class));
        return respond().asJson(gson.toJson(ImmutableMap.of("success", true)));
    };

    Action view = req -> respond().asJson(gson.toJson(
            personService.get(Long.valueOf(req.url().get("id")))
    ));

    Action update = req -> {
        final Long id = Long.valueOf(req.url().get("id"));
        // todo finish me
        return respond().withModel("person", personService.get(id));
    };

    Action delete = req -> {
        final Long id = Long.valueOf(req.url().get("id"));
        // todo finish me
        return respond().withModel("person", personService.get(id));
    };
}
