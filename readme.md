# m410 web application framework

This is a Restful, action based, back to basics, java 8 web framework.

Why compile time checks, catch bugs sooner by using the compiler.  IOC frameworks are great, and I intend to
support them, but they can only provide runtime type checking.

reflection overkill, annotation overkill

## Setup

### Prerequisites

  * You must have Java 8 ea JDK installed and set as the default.
  * You'll need Maven 3, It might work with earlier version, but I haven't tried

Check out the bootstrap app with this maven command.

    > mvn archetype:generate\
       -DarchetypeRepository=http://repo.m410.org/content/repositories/releases\
       -DarchetypeGroupId=org.m410.garden.arch\
       -DarchetypeArtifactId=m410-garden-bootstrap\
       -DarchetypeVersion=0.2.0\
       -DgroupId=<your_group>\
       -DartifactId=<your_artifact>\
       -Dversion=<your_version>

There is not much functionality available beyond the what's in the demonstration app. So documentation is
light for now.


## Application Class
See the application class documentation.
[http://m410.org/javadoc/current/org/m410/j8/application/Application.html](http://m410.org/javadoc/current/org/m410/j8/application/Application.html)


## Controller Classes
See the controller class documentation.
[http://m410.org/javadoc/current/org/m410/j8/controller/Controller.html](http://m410.org/javadoc/current/org/m410/j8/controller/Controller.html)

### Actions

execute of a uri.

### Path Expressions

an action is mapped to a path expression.

#### Path Parameters

Parameters embedded in the uri.

### Http Method and Content type action selection

One action path can delegate to more than one action depending on the http method and the content type.

### Action Responses

The response object.

### File upload and download

Built on top of commons file upload.

### Securing Actions

The Authorization interface.

### Action intercepting

For handling all actions in a similar fashion

## Transaction Scope

Transaction are executed one one of three scopes or not at all.

 - Action and View: wraps a request and the page rendering with a single transaction.
 - Action: This is the default transaction scope, it wraps the action call in a transaction, not page rendering.
 - Service: Services can be wrapped with a proxy and have method call intercepted.  This can work with the No
    Transaction scope to provided transactions with even smalled scopes than the action scope.
 - No transaction: When and action is marked with no scope a transaction is not used.


##
## ORM mapping
See the orm builder docs.
[http://m410.org/javadoc/current/org/m410/j8/module/ormbuilder/orm/EntityFactory.html](http://m410.org/javadoc/current/org/m410/j8/module/ormbuilder/orm/EntityFactory.html)



