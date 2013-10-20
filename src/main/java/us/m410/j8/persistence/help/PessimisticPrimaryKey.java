package us.m410.j8.persistence.help;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
abstract public class PessimisticPrimaryKey<T> implements Id<T>{
    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

}
