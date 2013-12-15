package org.m410.j8.module.ormbuilder.orm;

/**
 * Creates an Entity object that will be embedded in the orm.xml.  Typically
 * a dao service or an interface that is implemented by the dao service class
 * will implement this.  In the case of an interface you may implement the it
 * as a default method and add it to you service.
 *
 * <pre>
 * public class Person extends OptimisticLockingId&lt;Long&gt; {
 *      private String email;
 *      public String getEmail() { return email; }
 *      public void setEmail(String email) { this.email = email; }
 *      public String toString() {
 *          return new ToStringBuilder(this)
 *              .append("id",getId())
 *              .append("email", email)
 *              .toString();
 *      }
 *  }
 *
 *  interface PersonEntityFactory extends EntityFactory {
 *      default Entity makeEntity() {
 *          return entity("org.m410.arch.person.Person","person")
 *              .id("id", generatedValue("SEQUENCE", "generator"), sequenceGenerator("generator", "person_seq"))
 *              .version("version")
 *              .basic("email", column("email",124))
 *              .make();
 *      }
 *  }
 *  class MyPersonServiceImpl implements PersonDao<Person,Long>, PersonEntityFactory {}
 * </pre>
 *
 * See the ORM class for more details on creating the mapping.
 * <p>
 * Once you've defined the orm mapping it needs to be added to the application with
 * the OrmBuilderModule so it can be picked up by the build process to generate
 * the orm.xml.
 *
 *  <pre>
 *  class MyApplication extends Application implements OrmBuilderModule{
 *      // ...
 *      private PersonDao personDao = new MyPersonServiceImpl();
 *      public List<EntityFactory> entityBuilders() {
 *          return ImmutableList.of(personDao);
 *      }
 *  }
 *  </pre>
 *
 * @see org.m410.j8.module.ormbuilder.orm.ORM
 * @author Michael Fortin
 */
public interface EntityFactory {

    /**
     * Create an entity for inclusion in the orm.xml file.
     *
     * @return a Mapped entity bean.
     */
    Entity makeEntity();
}
