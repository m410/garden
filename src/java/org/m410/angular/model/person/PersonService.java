package org.m410.angular.model.person;

import org.m410.garden.module.auth.AuthorizationProvider;

import java.util.List;


public interface PersonService extends AuthorizationProvider {

    Person get(Long id);

    List<Person> list();

    Person save(Person person);

    void delete(Person person);

    Person findBy(String userName);

//    Optional<Set<ConstraintViolation>> validate(Person p);
}
