package org.m410.garden.module.auth;

/**
 * @author m410
 */
public class User {

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
}
