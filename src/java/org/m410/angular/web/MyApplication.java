package org.m410.angular.web;

import org.m410.angular.model.address.*;
import org.m410.angular.model.person.*;

import com.google.common.collect.ImmutableList;

import org.m410.garden.application.Application;
import org.m410.garden.configuration.Configuration;
import org.m410.garden.controller.HttpCtrl;
import org.m410.garden.controller.auth.AuthenticationController;
import org.m410.garden.module.jpa.JpaModule;
import org.m410.garden.module.ormbuilder.OrmBuilderModule;
import org.m410.garden.module.ormbuilder.orm.EntityFactory;

import java.util.List;


/**
 * Example Basic application.
 */
public final class MyApplication extends Application implements OrmBuilderModule, JpaModule  {
    PersonDao personDao = new PersonDaoImpl();
    PersonService personService = new PersonServiceImpl(personDao);
    AddressDao addressDao = new AddressDaoImpl();
    AddressService addressService = new AddressServiceImpl(addressDao);


    @Override
    public List<? extends HttpCtrl> makeControllers(Configuration c) {
        return ImmutableList.of(
                new HomeController(),
                new PersonController(personService),
                new AddressController(addressService,personService),
                new AuthenticationController<Person>(personService)
        );
    }

    /**
     * This makes the orm and persistence xml builders available to the build
     * in the maven plugin.
     */
    @Override
    public List<? extends EntityFactory> entityBuilders() {
        return ImmutableList.of(personDao, new Address());
    }
}
