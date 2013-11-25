package org.m410.j8.configuration;

import java.util.Map;

/**
 * Build definition from the configuration file.
 *
 * <ul>
 *    <li>default_task: "package"                          </li>
 *    <li>compiler_type: "scala"                           </li>
 *    <li>compiler_version: "2.10.0"                       </li>
 *    <li>compiler_args: ""                                </li>
 *    <li>target_dir: "{working_dir}/target"               </li>
 *    <li>webapp_dir: "{working_dir}/webapp"               </li>
 *    <li>package_classifier: ""                           </li>
 *    <li>package_source: false                            </li>
 *    <li>package_doc: false                               </li>
 *    <li>deploy_target: ""                                </li>
 *    <li>publish_target: ""                               </li>
 *    <li>version_control: ""                              </li>
 *    <li>fab_cache_dir: "{user.home}/.fab"                </li>
 *    <li>fab_home_dir: "{env.FAB_HOME}"                   </li>
 *    <li>builder_dir: "{working_dir}/.fab"                </li>
 *    <li>builder_cache_dir: "{builder_dir}/fab"           </li>
 *    <li>builder_code_dir: "{builder_dir}/fab_code"       </li>
 *    <li>builder_lib_dir: "{builder_dir}/fab_lib"         </li>
 *    <li>source_dir: "{woking_dir}/src/scala"             </li>
 *    <li>resource_dir: "{woking_dir}/src/resources"       </li>
 *    <li>classes_dir: "{target_dir}/classes"              </li>
 *    <li>test_source_dir: "{working_dir}/test/scala"      </li>
 *    <li>test_resource_dir: "{working_dir}/test/resources"</li>
 *    <li>test_classes_dir: "{target_dir}/test-classes"    </li>
 *    </ul>
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
