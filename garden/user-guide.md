#### Application class dependency Injection

The application class is an important part IOC container.  All service and controller
initialization happens here.

    public final MyController extends Application {
        MyService myService = new MyServiceImpl();

        public makeControlllers() {
            ImmutableList.of(
                new MyController(myService);
            );
        }
    }

### Multiple Inheritance with modules


## Transactions

Services that use interfaces have a simple way to create a proxy for transaction management.

    public final MyApplication extends Application implements JpaModule {
    MyService service = proxy(MyService.class, new MyServiceImpl(), new LocalHandler());
    public List<Ctrl> makeControllers(ImmutableHierarchicalConfiguration c) {
        return ImmutableList.of(
            new MyController(service);
        );
    }
    }

With this method you would access a threadLocal in your services to get access to the
current EntityManager like this

    public MyService implements MyServiceImpl {
        public save(Entity e) {
            JpaThreadLocal.get().persist(Entity.class, e);
        }
    }


## Inversion of control container

As described above, services are programmatically injected into your controllers.  The
application class also takes advantage of default methods in java 8 so that modules have
the ability to add services controllers and other functionality to you application.

### Controllers

Are the basic building block of an m410 web application.  I’ll start with an example:

    public final MyController extends Controller {
        public MyController() {
            super(“controllers/{controllerId}”);
        }
        public <? extends HttpActionDefinition> actions() {
            return Arrays.asList(
                get(“actions/{actionId}”,myAction)
            );
        }
        Action myAction = r -> respond().asText(“Hello”);
    }

This is a real basic example, but it has most of the major parts.  The class must extends the
Controller class, you must override one constructor to set the base path expression, you must
implements the actions() method, use one of the many built in http method names, in this case
get, and provide your own action implementation for that action.

## Constructor Base Path Expression

See path below for more details.  The controller base path is the prefix for all actions in
the controller class.

## HTTP Method

There are five methods for creating an action definition, all but one are named after a
http method.  The one exception is act() which is the method each of the shortcut methods call
using the specific http method.

It is perfectly acceptable to have two or more http methods apply to the same path.

    public <? extends HttpActionDefinition> actions() {
        return Arrays.asList(
            get(“actions/{actionId}”,myAction)
            post(“actions/{actionId}”,myPost)
        );
    }

## Content type

An action can be further refined by setting acceptable content types.

    public <? extends HttpActionDefinition> actions() {
        return Arrays.asList(
            get(“actions/{actionId}”,txtAction).contentTypes(“text/plain”),
            get(“actions/{actionId}”,htmlAction).contentTypes(“text/html”)
        );
    }

The contentTypes method takes a variable arguments.  You can have two actions with the same path
expression and http method but different contentTypes.

## Path Expressions
Path expressions are created by prefixing the the controller path with the action path.
Path expressions are the request URI without the context name.  Path parameters are names
wrapped in curly braces.

    “/controller/{controllerId}/actions/{actionId}”

the above is an example of the combined controller and action paths from the example
controller above.  In this case neither the controllerId or the actionId parameters have
regular expressions so anything will match.  It’s effectively the same as using the
regex ‘.*’  To match only on numbers you can add a regular expression like this:

    “/controller/{controllerId:\\d+}/actions/{actionId:\\d+}”

You can have two different paths using regular expressions, the example below will work
as a in adjacent to the one above.

    “/controller/{controllerName:\\w+}/actions/{actionName:\\w+}”

Path variable are accessible from the ActionRequest object via the method Map<String,String> url().

### Action

An action is a Action interface which is a single method function.  It takes an ActionRequest
object as an argument and expects a Response object to be returned.  Both of these are valid actions.

    Action action = (ActionRequest ar) -> {
        Person person= service.get(ar.request(“id”)[0]);
        return respond().asJson(gson.parse(person));
    };

    Action action2 = req -> respond().withModel(“name”,”val”).withView(“index”);


## Action request

The action request object is an interface that wraps the HttpServletRequest object.  It has helper
methods to get the request body as string or binary, to get params out of the url and other standard properties.

TODO doc reference

## Response

The response object is an immutable class with several helper setter methods to add new values to the response.

TODO  doc reference

## Uploading and Downloading json/xml and files

This is an example of uploading json and using google Gson to populate an object. saving it, and returning
json as a response.

    Action action2 = req -> {
        Gson gson = new Gson()
        Person in = gson.fromJson(req.asText(),Person.class);
        Person out = myService.save(in);
        return respond().asJson(gson.toJson(out))
    }

## Binary File upload and download

Posting data must be done with an http post.  The body of the post is available using the
ActionRequest.bodyAsFile() and ActionRequest.bodyAsString() methods.  the bodyAsFile method uses apache
commons file upload under the covers.  File upload and download is done like so.

    Action action2 = req -> {
        File file = req.bodyAsFile();
        // do something with the binary data…
        OutputStream largeData = myService.getDataSomehow();
        return respond().contentType(“application/zip”).stream(out ->{
        out.write(largeData)});
    }

## Securing Controllers

To Secure a controller it must implement the Authorization interface.  You can set the allowed roles
in the controller or in each action definition.

    public final MyController extends Controller implements Authorization {
        public MyController() {
            super(“controllers/{controllerId}”);
            allowedRoles = new String[]{“ADMIN”,”USER”};
            ssl = Ssl.Always;
        }

        public <? extends HttpActionDefinition> actions() {
            return Arrays.asList(
                get(“actions/{actionId}”,myAction).roles(“ADMIN”)
            );
        }
        Action myAction = r -> respond().asText(“Hello”);
    }
