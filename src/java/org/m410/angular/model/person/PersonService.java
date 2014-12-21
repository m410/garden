package org.m410.angular.model.person;

import org.m410.garden.controller.auth.AuthenticationProvider;
import org.m410.garden.controller.auth.AuthorizationStatus;

import java.util.List;


public interface PersonService extends AuthenticationProvider<Person> {

    Person get(Long id);

    List<Person> list();

    Person save(Person person);

    void delete(Person person);

    Person findBy(String userName);

}
