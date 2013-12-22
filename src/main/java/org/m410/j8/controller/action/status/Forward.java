package org.m410.j8.controller.action.status;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.m410.j8.servlet.ServletExtension;

/**
 * A request action that must be forwarded.
 *
 * @author Michael Fortin
 */
public class Forward implements ActionStatus, ServletExtension {
    private String forwardPath;

    public Forward(String path) {
        this.forwardPath = path+SERVLET_EXT;
    }

    public String getPath() {
        return forwardPath;
    }

    @Override
    public int id() {
        return FORWARD;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("path", forwardPath).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Forward)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Forward that = (Forward) obj;
        return new EqualsBuilder()
                .append(this.id(), that.id())
                .append(this.forwardPath, that.forwardPath)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13,17).append(this.id()).toHashCode();
    }
}

