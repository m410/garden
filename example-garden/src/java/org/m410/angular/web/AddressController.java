package org.m410.angular.web;

import com.google.common.collect.ImmutableList;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.m410.angular.model.address.Address;
import org.m410.angular.model.address.AddressService;
import org.m410.angular.model.person.Person;
import org.m410.angular.model.person.PersonService;
import org.m410.garden.controller.Controller;
import org.m410.garden.controller.action.http.Action;
import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author m410
 */
public final class AddressController extends Controller {
    private final Logger log = LoggerFactory.getLogger(AddressController.class);
    private AddressService addressService;
    private PersonService personService;

    private final Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {

                public boolean shouldSkipClass(Class<?> clazz) {
                    return (clazz == Person.class);
                }

                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getName().toLowerCase().contains("person");
                }

            }).create();


    public AddressController(AddressService addressService,PersonService personService) {
        super("/persons/{personId:\\d+}/addresses");
        this.addressService = addressService;
        this.personService = personService;
    }

    @Override
    public List<? extends HttpActionDefinition> actions() {
        return ImmutableList.of(
            get("", list),
            post("", create),
            get("{addressId:\\d+}", detail),
            delete("{addressId:\\d+}", delete)
        );
    }

    Action create = req ->{
        final Address address = gson.fromJson(req.bodyAsString(), Address.class);
        address.setPerson(personService.get(Long.valueOf(req.url().get("personId"))));
        addressService.save(address);
        return respond().asJson("{\"success\":true}");
    };

    Action delete = req ->{
        addressService.delete(Long.valueOf(req.url().get("addressId")));
        return respond().asJson("{\"success\":true}");
    };

    Action list = req -> {
        final Long id = Long.valueOf(req.url().get("personId"));
        return respond().asJson(gson.toJson(addressService.listByPersonId(id)));
    };

    Action detail = req -> {
        final Long id = Long.valueOf(req.url().get("addressId"));
        return respond().asJson(gson.toJson(addressService.get(id).get()));
    };

}
