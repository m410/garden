package org.m410.garden.application.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to to call a service to shutdown.
 * Must be a no args method with no return type.
 * <code>
 *     &#64;Shutdown
 *     void shutdownMyService();
 * </code>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Shutdown {

}
