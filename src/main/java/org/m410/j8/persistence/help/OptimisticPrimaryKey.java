package org.m410.j8.persistence.help;

/**
 * Document Me..
 *
 *  If your concerned about tightly cupling your code to this framework don't use this.
 *
 * @author Michael Fortin
 */
abstract public class OptimisticPrimaryKey<T> extends PessimisticPrimaryKey<T> {
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
