m410 web application framework
======
This is a Restful, action based, back to bases java 8 web framework.

Setup
------
With maven.

    > mvn archetype:generate\
       -DartifactRepository=http://repo.m410.org/content/repositories/releases\
       -DarchetypeGroupId=org.m410.seed\
       -DarchetypeArtifactId=m410-seed\
       -DarchetypeVersion=0.1\
       -DgroupId=<your_group>\
       -DartifactId=<your_artifact>\
       -Dversion=<your_version>

Or clone the seed app.

    > git clone git@github.com/m410/m410-seed.git

Configuration
------
The configuration.m410.yml file contains all configuration for the application with
the exception of dependencies.  Those are maintained in the pom.xml file (for now).



Application Class
------
See the application class documentation.

http://m40.org/javadoc/current/org/m410/j8/application/Application.html


Controller Classes
------
See the controller class documentation.

http://m40.org/javadoc/current/org/m410/j8/controller/Controller.html
