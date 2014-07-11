package org.m410.demo;

import org.m410.garden.app.Application;


public final class MyApp extends Application implements JpaComponent, FlywayComponent, MailComponent {

    final MyDao myDao = jpaTrxProxy(MyDao.class, new MyDaoImpl(), this);
    final MyService myService = trxProxy(MyService.class, new MyServiceImpl(myDao));
    final MailService mailService = trxProxy(componentService(MailService.class));

    @Override
    Collection<ManagedService> managedServices(final Configuration config) {
	return ImmutableList.of(new ManagedService(){
	    void start() { myService.doStartup();}
	    void shutdown() {myService.doShutdown();}
	});
    }

    @Override
    Collection<Controller> controllers(final Configuration configuration) {
        return ImmutableList.of(
	    new MyController(myService),
	    trxInViewProxy(new MyViewController(myService))
	);
    }

    // @ServiceComponent
    // @ControllerComponent
    // @InterceptorComponent
    // @ManagedServiceComponent
    // @PostInitializeComponent
    // @ServletDefinitionComponent
    // @FitlerDefinitionComponent
    // @ListenerComponent
}
