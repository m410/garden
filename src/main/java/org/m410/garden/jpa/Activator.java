package org.m410.garden.jpa;

import org.m410.fabricate.service.FabricateService;
import org.m410.garden.jpa.internal.OrmXmlTask;
import org.m410.garden.jpa.internal.PersistenceXmlTask;
import org.osgi.framework.*;

/**
 * @author Michael Fortin
 */
public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        ServiceReference fabricateServiceReference = context.getServiceReference(FabricateService.class.getName());
        FabricateService fabricateService = (FabricateService) context.getService(fabricateServiceReference);

        fabricateService.addCommandModifier(command -> {
            if(command.getName().equalsIgnoreCase("build")) {
                command.getSteps().stream()
                        .filter(m->m.getName().equals("generate-resources"))
                        .findFirst()
                        .ifPresent(m->{
                            m.append(new PersistenceXmlTask());
                            m.append(new OrmXmlTask());
                        });
            }
        });
    }

    public void stop(BundleContext context) throws Exception {

    }

}
