SLF4J logging module for garden 
=================================

Adds Logback support for garden applications.  

Example configuration

    logging(org.m410.garden:garden-logback:0.3-SNAPSHOT):
      logger:
      - name: org.m410
        level: TRACE
      root:
        level: DEBUG
        ref: [STDOUT]

