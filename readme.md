# m410 web application framework

This is a Restful, action based, back to basics, java 8 web framework.

Why compile time checks, catch bugs sooner by using the compiler.  IOC frameworks are great, and I intend to
support them, but they can only provide runtime type checking.

reflection overkill, annotation overkill

## Setup

### Prerequisites

  * Java 8 JDK installed and set as the default.
  * Fab(ricate) installed and working.

There is not much functionality available beyond the what's in the demonstration app. So documentation is
light for now.


## Application Class
See the application class documentation.
[http://m410.org/javadoc/current/org/m410/j8/application/Application.html](http://m410.org/javadoc/current/org/m410/j8/application/Application.html)

The action class is the central wiring and initialization point of the application.  It’s available in the servlet context if needed.  You must set the class name in application configuration so it can be initialized at runtime.  Also, by convention, for the application to be able to reload automatically in local development mode, you also must also declare the Application Loader class.  It should be created automatically when generating a project.  It has to be in the same class loader as the application class to able to reload the application.

A basic Application class looks like this

    public final class MyApplication extends Application {
        @Override public List<? extends HttpCtrl> makeControllers(Configuration c) {
                   return ImmutableList.of(new HomeController());
            }
    }


### Service Providers
An application can wire in service providers by implementing an interface with an annotated default
method that follows a conventional signature.

    public interface JpaModule extends ApplicationModule {  
        @ThreadLocalComponent 
        default List<? extends ThreadLocalSessionFactory<?>> makeJpaThreadLocal(Configuration c) { 
            return ImmutableList.of( 
                new HibernatePersistence(c) 
            ); 
        }
     }

This Service provider module can be wired to the application like this:

    public final class MyApplication extends Application implements JpaModule {}

This will create a hibernate thread local manager and make it averrable to all requests.

### Injecting Services
Creating a service is as simple as declaring it and injecting it as a dependency on other classes.

    public final class MyApplication extends Application implements JpaModule {
        PersonDao personDao = new PersonDaoImpl();
            PersonService personService = new PersonServiceImpl(personDao);

    @Override public List<? extends HttpCtrl> makeControllers(Configuration c) {
            return ImmutableList.of(
                    new HomeController(),
                    new PersonController(personService)
            );
        }
    }


### Service Life cycle
Services that need to have life cycle events called can be wrapped into the lifecycle interface.

    public final class MyApplication extends Application implements JpaModule {
        PersonDao personDao = new PersonDaoImpl();
            PersonService personService = new PersonServiceImpl(personDao);

    @Override public List<LifeCycle> manageLifeCycle(Configuration c) {
            return ImmutableList.of(
                    new LifeCycle(){
            public void initialize(){personService.init();}
            public void destroy(){personService.shutdown();}
            }
            );
        }
    }

### Transactions
See controller transactions for a better description of transaction scopes.  But for an application
you can add transaction to a service by using the transactional method proxy.

    public final class MyApplication extends Application implements JpaModule {
        PersonDao personDao = new PersonDaoImpl();
            PersonService personService = transactional(
                PersonService.class,
                new PersonServiceImpl(personDao));

    @Override public List<? extends HttpCtrl> makeControllers(Configuration c) {
            return ImmutableList.of(
                    new HomeController(),
                    new PersonController(personService)
            );
        }
    }


This will wrap all calls to the PersonServiceImpl class with any threadLocal created by a
ThreadLocalFactory in the Application class.  You can add your own threadLocals by overriding

    List<? extends ThreadLocalSessionFactory> makeThreadLocalFactories(Configuration c)


### Creating Controllers
Just like in the very first example, you wire them into the `makeControllers` method of the application.  The
`makeControllers` method is the only method of the Application class that needs to be implemented.




## Controllers
See the controller class documentation.
[http://m410.org/javadoc/current/org/m410/j8/controller/Controller.html](http://m410.org/javadoc/current/org/m410/j8/controller/Controller.html)


Your custom controllers must extend the abstract Controller class.  The abstract class overrides the
default construct so you must implement your own.  In that constructor you need to call the super
constructor with the base path expression. There is a single method that must be implemented called
`actions()` to wire the action to the path.

### RESTful urls and web services
Controllers use uri path expressions to first match a request uri.  Once the uri is matched, then the
http method is matched along with  the content type.

	class MyController extends Controller {
		public MyController(){
			super(“persons/{personId:\\d+}/books”);
		}

		public List<HttpActionDefinition> actions() { 	    		return ImmutableList.of(
				get(“{bookId:\\d+}”,getBook)
			)
		}
		Action getBook = req-> {
			return respond().withModel(“book”,book).withView(“/books/view.jsp”);
		}
	}

For this example the controller has a base path of “persons/personId/books”.  This will match the
uri `“/persons/123/books”`.  Once that match is made the action must match.  I this case the book
id.  So an http `GET` request with the uri `“/persons/123/books/123”` will match the getBook action.
  A book instance will be placed as the model to the view.  In this example only numbers are
  matched, letters will not. so the uri `“/persons/1a/books”` will not match this controllers path.

The call to `get(“{bookId:\\d+}”,getBook)` could be further refined with content type.


### Security, Authentication and Authorization

Authentication and Authorization is performed by a few classes in the `org.m410.garden.controller.auth` package.

#### AuthorizationModule
The application itself must implement this interface so actions know that this application requires certain services.

#### AuthenticationProvider
This is an interface required by the `AuthenticationController` that makes the authentication service accessible.

#### AuthenticationController
A stand alone controller instance that can be wired into your application to manage authentication.  You
 don’t have to use it, you can write your own.

#### Authorizable
Interface to add to any controller that requires authorization.


A full example will look something like this

    public final class MyApp extends Application implements AuthModule {
        MyServiceDao myServiceDao = new MyServiceDaoImpl(); 	MyService myService = new MyServiceImpl(myServiceDao);

        public AuthenticationProvider getAuthenticationProvider(){
            return new AuthenticationProvider(){
                // implement by calling a service in the model package.
            }
        }

        @Override public List<? extends HttpCtrl> makeControllers(Configuration c) { 
            return ImmutableList.of(
                new AuthenticationController(getAuthenticationProvider()),
                new HomeController(), 
                new MyController(myService) 
            ); 
        }
    }

In this example the HomeController would be a public splash page, login is performed by the AuthController, and
MyController has a protected resource, because it implements Authorizable.


### XML, JSON, and Binary Files
To return xml or json as the body of an http request, use one of the two helper methods on the Response object.

	class MyController extends Controller {
		Action json = req -> respond().asJson(“{}”);
		Action xml = req -> respond().asXml(“<body></body>”);
	}

You can have more than one action with the same path and http method but accepting different content types.

	class MyController extends Controller {
		public List<HttpActionDefinition> actions() { 
		    return ImmutableList.of(
				post(“”,saveJson).accept(“application/json”),
				post(“”,saveXml).accept(“application/xml”)
		}
		Action saveJson = req -> respond().withView(“index.jsp”);
		Action saveXml = req -> respond().withView(“index.jsp”);
	}

By default a action definition accepts all content types.

Binary file upload and download is taken care of by apache file-upload under the covers.  You can access content with:

	class MyController extends Controller {
		public List<HttpActionDefinition> actions() { 
		    return ImmutableList.of(post(“”,save))
		}
		Action save = req-> {
			Inputstream in = req.getBodyAsStream();
			return respond().asStream(out->out.write(“someval”));
		}
	}


## Transactions
Ideally, transactions should be handled at the smallest scope possible, within your service that is
inject.  You do have the option to define transactions at the action level in the controller and with action and view.

### Service scope Transactions
Services can be defined in you application like this.

	MyService myService = transaction(MyService.class, new MyServiceImpl());
	new MyController(myService);

This with use the java Proxy class to make a proxy around the interface.  This can not be injected
into a controller as a standard class.

### Action scope Transactions
Transactions can be defined at the Action level as well.  To do this create a controller and in the
action definition declare it as transactional.

	class MyController extends Controller {
		public List<HttpActionDefinition> actions() { 
		    return ImmutableList.of(
				get(“”,list).transaction();
		}
		Action list = req -> respond().withView(“index.jsp”);
	}

With this method the body of the list Action will have access to a threadLocal with the transaction created
for that scope.

### Action and View Transaction scope
This is the largest scope, it will wrap a transaction over the body of the action and rendering of the view.
This should only be used if you access JPA entities in a view (jsp,freemarker, etc) and need to retrieved
lazily loaded relations.

	class MyController extends Controller {
		public List<HttpActionDefinition> actions() { 
		    return ImmutableList.of(
				get(“”,list).transaction(TransactionalScope.ActionAndView);
		}
		Action list = req -> respond().withView(“index.jsp”);
	}

## Action Interceptors
Any Controller can implement the Intercept interface to wrap every action call.

    class MyController extends Controller implements Intercept {
        @Override public Response intercept(ActionRequest actionRequest, Action action) {
	        return action.execute(actionRequest);
        }

		public List<HttpActionDefinition> actions() { 
		      return ImmutableList.of(
				get(“”,list).transaction(TransactionalScope.ActionAndView);
		}
		Action list = req -> respond().withView(“index.jsp”);
	}


## ORM mapping
See the orm builder docs.
[http://m410.org/javadoc/current/org/m410/j8/module/ormbuilder/orm/EntityFactory.html](http://m410.org/javadoc/current/org/m410/j8/module/ormbuilder/orm/EntityFactory.html)



