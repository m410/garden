package org.m410.garden.serialize;


/**
 * This is intended to be statically imported into a controller for easy
 * serialization of objects to and from json.
 *
 *  @author Michael Fortin
 */
public class Json {
    public static <T> String toJson(Class<T> clazz, T instance) {
        return null;
    }

    public static <T> T fromJson(Class<T> clazz, String json) {
        return null;
    }
}
