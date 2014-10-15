package org.m410.garden.logback;

import org.m410.fabricate.service.FabricateService;
import org.m410.garden.logback.internal.LogbackXmlTask;
import org.osgi.framework.*;

/**
 * @author Michael Fortin
 */
public class Activator implements BundleActivator {
    ServiceRegistration fabricateServiceRegistration;

    public void start(BundleContext context) throws Exception {
        ServiceReference fabricateServiceReference = context.getServiceReference(FabricateService.class.getName());
        FabricateService fabricateService = (FabricateService) context.getService(fabricateServiceReference);

        fabricateService.addCommandModifier(command -> {
            if(command.getName().equalsIgnoreCase("build")) {
                command.getSteps().stream()
                        .filter(m->m.getName().equals("generate-resources"))
                        .findFirst()
                        .ifPresent(m->{
                            m.append(new LogbackXmlTask());
                        });
            }
        });
    }

    public void stop(BundleContext context) throws Exception {
        fabricateServiceRegistration.unregister();
    }

}
