package org.m410.garden.jetty9;


import org.m410.fabricate.builder.Command;
import org.m410.fabricate.builder.Step;
import org.m410.fabricate.service.FabricateService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Michael Fortin
 */
public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        ServiceReference fabricateServiceReference = context.getServiceReference(FabricateService.class.getName());
        FabricateService fabricateService = (FabricateService) context.getService(fabricateServiceReference);

        fabricateService.addCommand(new Command("jetty9", "Run jetty", false)
                .withStep(new Step("initialize"))
                .withStep(new Step("run")
                                .append(new Jetty9Task())
                                .append(new WatchStaticTask())
                         ));

    }

    public void stop(BundleContext context) throws Exception {
    }
}
