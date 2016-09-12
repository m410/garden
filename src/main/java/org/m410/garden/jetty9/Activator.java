package org.m410.garden.jetty9;


import org.m410.fabricate.builder.Command;
import org.m410.fabricate.builder.Step;
import org.m410.fabricate.service.FabricateService;
import org.osgi.framework.*;

/**
 * @author Michael Fortin
 */
public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        ServiceReference fabricateServiceReference = context.getServiceReference(FabricateService.class.getName());
        FabricateService fabricateService = (FabricateService) context.getService(fabricateServiceReference);

        fabricateService.addCommand(new Command("jetty9", "Run jetty", false)
                .withStep(new Step("default").append(new Jetty9Task())));

    }

    public void stop(BundleContext context) throws Exception {
    }
}
