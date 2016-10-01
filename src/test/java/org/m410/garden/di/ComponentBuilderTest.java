package org.m410.garden.di;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.m410.garden.di.app.sample.SampleComponent;
import org.m410.garden.fixtures.MyServiceDao;
import org.m410.garden.zone.MyZoneFactory;
import org.m410.garden.zone.ZoneHandlerFactory;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * @author Michael Fortin
 */
public class ComponentBuilderTest {

    private List<ZoneHandlerFactory> zones = ImmutableList.of(new MyZoneFactory(null).zoneHandlerFactory());

    @Test
    public void isRegistered() throws Exception {
        final ComponentBuilder componentBuilder = new SampleComponent().builders().get(1);
        assertTrue(componentBuilder.getTargetClass().equals(MyServiceDao.class));
        assertFalse(componentBuilder.isRegistered(new TreeSet<>()));

        SortedSet<Components.Entry> entries = new TreeSet<>();
        entries.add(new Components.Entry(MyServiceDao.class, null));
        assertTrue(componentBuilder.isRegistered(entries));
    }

    @Test
    public void canCreateWith() throws Exception {
        final ComponentBuilder componentBuilder = new SampleComponent().builders().get(1);
        assertTrue(componentBuilder.canCreateWith(new TreeSet<>()));

        final ComponentBuilder componentBuilder2 = new SampleComponent().builders().get(0);
        assertFalse(componentBuilder2.canCreateWith(new TreeSet<>()));
    }

    @Test
    public void createWith() throws Exception {
        final ComponentBuilder componentBuilder = new SampleComponent().builders().get(1);
        final Components.Entry entry = componentBuilder.createWith(zones, new TreeSet<>());
        assertNotNull(entry);
        assertNotNull(entry.getInstance());
    }
}