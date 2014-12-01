JPA hibernate persistence module for garden project
=================================

This adds support for generating the persistence.xml and orm.xml files for deployment.

To enable it, add this to the configuration.

    persistence:
      - name: garden-jpa
        organization: org.m410.garden
        version: "0.1"
        user_orm_builder: false
        properties:
          hibernate.dialect: org.hibernate.dialect.H2Dialect
          hibernate.connection.driver_class: org.h2.Driver
          hibernate.connection.username: sa
          hibernate.connection.password: ""
          hibernate.connection.url: jdbc:h2:~/demo-app;AUTO_SERVER=TRUE
          hibernate.hbm2ddl.auto: update


And add this to you Application class.

    public final class MyApplication extends Application implements JpaModule {
        // ...
    }


## ORM Builder
There is an optional orm builder which is deactivated by default to enable it set `user_orm_builder: true`.


