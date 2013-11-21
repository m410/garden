package org.m410.j8.action.direction;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class View implements Direction {
    private String path;

    public View(String path) {
        assert(path != null);
        this.path = path;
    }

    @Override
    public int id() {
        return VIEW;
    }

    public String getPath() {
        return path;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3,13).append(path).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof View)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        View that = (View) obj;
        return new EqualsBuilder().append(this.path, that.path).isEquals();
    }
}
