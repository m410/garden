package us.m410.j8.application;

import com.google.common.collect.ImmutableList;
import us.m410.j8.service.ThreadLocalComponent;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public interface LifeCycleComponent {

    void onStartup();

    void onShutdown() ;
}
