package org.m410.garden.jpa;

import org.m410.fabricate.service.FabricateService;
import org.m410.garden.jpa.internal.OrmXmlTask;
import org.m410.garden.jpa.internal.PersistenceXmlTask;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Michael Fortin
 */
public final class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        ServiceReference fabricateServiceReference = context.getServiceReference(FabricateService.class.getName());
        FabricateService fabricateService = (FabricateService) context.getService(fabricateServiceReference);

        fabricateService.addCommandModifier(command -> {
            if(command.getName().equalsIgnoreCase("build")) {
                command.getSteps().stream()
                        .filter(m->m.getName().equals("process-classes"))
                        .findFirst()
                        .ifPresent(m->{
                            m.append(new OrmXmlTask());
                            m.append(new PersistenceXmlTask());
                        });
            }
        });
    }

    public void stop(BundleContext context) throws Exception {
    }
}
