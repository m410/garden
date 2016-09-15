package org.m410.garden.serialize;


/**
 * This is intended to be statically imported into a controller for easy
 * serialization of objects to and from xml.
 *
 *  @author Michael Fortin
 */
public class Xml {
    public static <T> String toXml(Class<T> clazz, T instance) {
        return null;
    }

    public static <T> T fromXml(Class<T> clazz, String json) {
        return null;
    }
}
