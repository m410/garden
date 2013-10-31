package org.m410.j8.configuration;

import java.util.Map;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class BuildDefinition {
    private boolean packageSource;

    public boolean isPackageSource() {
        return packageSource;
    }

    public void setPackageSource(boolean packageSource) {
        this.packageSource = packageSource;
    }

    public static BuildDefinition fromMap(Map<String, Object> map) {
        BuildDefinition ad = new BuildDefinition();
        ad.setPackageSource(toBoolean(map.getOrDefault("packageSource","false")));
        // todo finish me
        return ad;
    }

    private static Boolean toBoolean(Object j) {
        if(j instanceof Boolean)
            return (Boolean)j;
        else
            return Boolean.valueOf((String)j);
    }
}
