package org.m410.garden.zone.transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.zone.transactional.fixtures.MyService;
import org.m410.garden.zone.transactional.fixtures.TrxApplication;
import org.m410.garden.zone.transactional.fixtures.Trx;

import static org.junit.Assert.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class TrxServiceTest {

    @Test
    public void createServiceWithTransaction() {
        TrxApplication app = new TrxApplication();
        app.init(null);
        assertNotNull(app.getZoneManager());
        assertEquals(1, app.getZoneManager().getZoneFactories().size());
        assertEquals(0, Trx.getCallCount());

        assertEquals(3,app.getComponents().typeOf(MyService.class).list().size());

        assertEquals(1, Trx.getCallCount());
    }
}
