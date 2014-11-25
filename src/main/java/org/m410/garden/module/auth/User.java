package org.m410.garden.module.auth;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author m410
 */
public final class User implements Serializable {

    private String userName;
    private String[] userRoles;

    public User(String userName, String[] userRoles) {
        this.userName = userName;
        this.userRoles = userRoles;
    }

    public String getUserName() {
        return userName;
    }

    public String[] getUserRoles() {
        return userRoles;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(userName).append(userRoles).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(7,3).append(userName).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) return false;
        User that = (User) obj;
        return new EqualsBuilder().append(this.userName,that.userName).isEquals();
    }
}
