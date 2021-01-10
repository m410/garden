package org.m410.garden.node;

import org.m410.fabricate.service.FabricateService;
import org.osgi.framework.*;

/**
 * @author Michael Fortin
 */
public final class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        ServiceReference fabricateServiceReference = context.getServiceReference(FabricateService.class.getName());
        FabricateService fabricateService = (FabricateService) context.getService(fabricateServiceReference);

        fabricateService.addCommandModifier(command -> {
            command.getSteps().stream()
                    .filter(m->m.getName().equals("initialize"))
                    .findFirst()
                    .ifPresent(m->m.append(new NodeInstallTask()));

        });
    }

    public void stop(BundleContext context) throws Exception {
    }
}
