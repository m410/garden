package org.m410.garden.module.mail;

import com.google.common.collect.ImmutableList;
import org.m410.garden.application.annotate.ComponentsProvider;
import org.m410.garden.di.ComponentBuilder;
import org.m410.garden.di.ComponentSupplier;
import org.m410.garden.di.Components;

/**
 * Add Mail sending a ability to an application.
 *
 * @author Michael Fortin
 */
public interface MailModule {

    @ComponentsProvider
    default ComponentSupplier makeMailService() {
        return (zoneManager, configuration) -> Components.init().add(() -> ImmutableList.of(
                ComponentBuilder.builder(MailService.class).factory((a, b) -> new MailService()))
                                                                    );

    }
}
