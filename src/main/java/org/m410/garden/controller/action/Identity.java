package org.m410.garden.controller.action;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.m410.garden.controller.auth.AuthorizationProvider;
import org.m410.garden.controller.auth.User;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Optional;

/**
 * Much like the servlet request principal object, except it's not null when
 * there is no principal.  Check the isAnonymous method to check if
 * if it's present or not.
 *
 * @author Michael Fortin
 */
public final class Identity implements Serializable {
    private final Optional<User> user;
    // calista

    public Identity(HttpServletRequest httpServletRequest) {

        if(httpServletRequest.getSession(false) != null) {
            this.user = Optional.ofNullable(
                    (User)httpServletRequest.getSession(false)
                            .getAttribute(AuthorizationProvider.SESSION_KEY)
            );
        }
        else {
            this.user = Optional.empty();
        }
    }

    /**
     * username if available.
     * @return name
     */
    public String getUserName() {
        if(user.isPresent())
            return user.get().getUserName();
        else
            return null;
    }

    /**
     * The users authorized roles.
     * @return a string array of roles, or an empty array.
     */
    public String[] getUserRoles() {
        if(user.isPresent())
            return user.get().getUserRoles();
        else
            return new String[]{};
    }

    /**
     * true if the session is anonymous.
     *
     * @return true if anonymous.
     */
    public boolean isAnonymous() {
        return !user.isPresent();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13,13).append(this.user).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;
        Identity that = (Identity) obj;
        return new EqualsBuilder().append(this.user, that.user).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(user).toString();
    }
}
