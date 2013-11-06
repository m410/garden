package org.m410.j8.configuration;

import java.util.Map;

/**
 *
 *   default_task: "package"
 compiler_type: "scala"
 compiler_version: "2.10.0"
 compiler_args: ""
 target_dir: "{working_dir}/target"
 webapp_dir: "{working_dir}/webapp"
 package_classifier: ""
 package_source: false
 package_doc: false
 deploy_target: ""
 publish_target: ""
 version_control: ""
 fab_cache_dir: "{user.home}/.fab"
 fab_home_dir: "{env.FAB_HOME}"
 builder_dir: "{working_dir}/.fab"
 builder_cache_dir: "{builder_dir}/fab"
 builder_code_dir: "{builder_dir}/fab_code"
 builder_lib_dir: "{builder_dir}/fab_lib"
 source_dir: "{woking_dir}/src/scala"
 resource_dir: "{woking_dir}/src/resources"
 classes_dir: "{target_dir}/classes"
 test_source_dir: "{working_dir}/test/scala"
 test_resource_dir: "{working_dir}/test/resources"
 test_classes_dir: "{target_dir}/test-classes"
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
