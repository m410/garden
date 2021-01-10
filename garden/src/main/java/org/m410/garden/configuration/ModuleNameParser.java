package org.m410.garden.configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Fortin
 */
public final class ModuleNameParser {
    private final Pattern modulePattern = Pattern.compile(
            "^(persistence|modules|views|testing|logging)\\((.*?):(.*?):(.*?)\\)$"
     );

    private final String moduleName;

    private String stereotype;
    private String name;
    private String org;
    private String version;

    public ModuleNameParser(String moduleName) {
        this.moduleName = moduleName;
        final Matcher matcher = modulePattern.matcher(moduleName.replaceAll("\\.\\.", ".").trim());

        if(matcher.find()) {
            stereotype = matcher.group(1);
            org = matcher.group(2);
            name= matcher.group(3);
            version= matcher.group(4);
        }
        else {
            throw new RuntimeException("could not parse module name: '" + moduleName +"'");
        }
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getStereotype() {
        return stereotype;
    }

    public String getName() {
        return name;
    }

    public String getOrg() {
        return org;
    }

    public String getVersion() {
        return version;
    }
}
