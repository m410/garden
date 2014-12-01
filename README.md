Jetty9 module for garden project
=================================

This adds the ability to run a project in development mode.  Meaning you can run jetty on
`http://localhost:8080/` and make code changes as usual and those changes will be immediately
reflected in the running application.


To configure it simply add this to the configuration

    modules:
      - name: garden-jetty9
        organization: org.m410.garden
        version: "0.1"


