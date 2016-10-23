package org.m410.garden.sass;

import org.m410.fabricate.builder.Command;
import org.m410.fabricate.service.FabricateService;
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
                addBuildTask(command);
            }
            else if(command.getName().startsWith("tomcat") || command.getName().startsWith("jetty") ) {
                addRunTask(command);
            }
        });
    }

    private void addRunTask(Command command) {
        command.getSteps().stream()
                .filter(m->m.getName().equals("run"))
                .findFirst()
                .ifPresent(m->m.append(new WatchSassTask()));
    }

    private void addBuildTask(Command command) {
        command.getSteps().stream()
                .filter(m->m.getName().equals("process-resources"))
                .findFirst()
                .ifPresent(m->m.append(new BuildSassTask()));
    }

    public void stop(BundleContext context) throws Exception {
    }
}
