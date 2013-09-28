package us.m410.j8.action;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class ActionStatusRedirect implements ActionStatus {
    private String path;

    ActionStatusRedirect(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public int id() {
        return REDIRECT_TO_SECURE;
    }
}
