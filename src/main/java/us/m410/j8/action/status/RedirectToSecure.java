package us.m410.j8.action.status;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class RedirectToSecure implements ActionStatus {
    private String path;

    public RedirectToSecure(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public int id() {
        return REDIRECT_TO_SECURE;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RedirectToSecure)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RedirectToSecure that = (RedirectToSecure) obj;
        return new EqualsBuilder()
                .append(this.id(), that.id())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(5,7).append(REDIRECT_TO_SECURE).toHashCode();
    }
}
