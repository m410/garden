package org.m410.garden.javascript;

import org.m410.fabricate.service.FabricateService;
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
            command.getSteps().stream()
                    .filter(m -> m.getName().equals("prepare-package"))
                    .findFirst()
                    .ifPresent(m -> m.append(new BuildJavascriptTask()));

//            else if(command.getName().startsWith("tomcat") || command.getName().startsWith("jetty") ) {
//                command.getSteps().stream()
//                        .filter(m->m.getName().equals("process-classes"))
//                        .findFirst()
//                        .ifPresent(m->m.append(new WatchJavascriptTask()));
//            }
        });
    }

    public void stop(BundleContext context) throws Exception {
    }
}
