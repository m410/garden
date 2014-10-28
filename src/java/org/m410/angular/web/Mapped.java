package org.m410.angular.web;

import org.m410.angular.model.person.Person;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.beanutils.BeanUtils.populate;

/**
 * @author m410
 */
public interface Mapped {
    default Person make(Map<String,String> params) {
        Person person = new Person();

        try {
            populate(person, params);
        }
        catch (IllegalAccessException|InvocationTargetException e) {
            throw new RuntimeException("person",e);
        }
        return person;
    }

}
