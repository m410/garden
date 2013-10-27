package org.m410.j8.action.status;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class RedirectToAuth implements ActionStatus {
    private String path;
    private String lastView;

    public RedirectToAuth(String path, String lastView) {
        this.path = path;
        this.lastView = lastView;
    }

    public String getPath() {
        return path;
    }

    public String getLastView() {
        return lastView;
    }

    @Override
    public int id() {
        return REDIRECT_TO_AUTH;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RedirectToAuth)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RedirectToAuth that = (RedirectToAuth) obj;
        return new EqualsBuilder()
                .append(this.id(), that.id())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13,13).append(this.id()).toHashCode();
    }
}
