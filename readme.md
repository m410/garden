# m410 web application framework

This is a Restful, action based, back to basics, java 8 web framework.

## Setup

### Prerequisites

  * You must have Java 8 ea JDK installed and set as the default.
  * You'll need Maven 3, It might work with earlier version, but I haven't tried

Check out the bootstrap app with this maven command.

    > mvn archetype:generate\
       -DarchetypeRepository=http://repo.m410.org/content/repositories/releases\
       -DarchetypeGroupId=org.m410.arch\
       -DarchetypeArtifactId=m410-bootstrap\
       -DarchetypeVersion=0.1.0\
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


## ORM mapping

See the orm builder docs.

[http://m410.org/javadoc/current/org/m410/j8/module/ormbuilder/orm/EntityFactory.html](http://m410.org/javadoc/current/org/m410/j8/module/ormbuilder/orm/EntityFactory.html)



