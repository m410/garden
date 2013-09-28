package us.m410.j8.action;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public final class ActionStatusRedirectAuth implements ActionStatus {
    private String path;
    private String lastView;

    ActionStatusRedirectAuth(String path, String lastView) {
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
}
