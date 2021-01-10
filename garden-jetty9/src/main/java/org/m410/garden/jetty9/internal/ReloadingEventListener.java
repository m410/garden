package org.m410.garden.jetty9.internal;

import java.util.EventListener;

/**
* @author m410
*/
public interface ReloadingEventListener extends EventListener {
    public void onChange(ReloadingEvent reloadingEvent);
}
