-- Initial setup script
-- see http://flywaydb.org

insert into person (id,version,name) values (nextval('person_seq'),0,'fred');
