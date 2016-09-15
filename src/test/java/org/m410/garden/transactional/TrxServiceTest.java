package org.m410.garden.transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.transactional.fixtures.TrxApplication;
import org.m410.garden.transactional.fixtures.TrxSession;

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
        assertNotNull(app.getThreadLocalFactories());
        assertEquals(1, app.getThreadLocalFactories().size());
        assertEquals(0, TrxSession.getCallCount());

        assertEquals(3,app.getMyService().list().size());

        assertEquals(1, TrxSession.getCallCount());
    }
}
