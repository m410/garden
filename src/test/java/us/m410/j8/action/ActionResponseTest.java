package us.m410.j8.action;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import us.m410.j8.action.direction.Directions;

import static org.junit.Assert.*;


/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ActionResponseTest {

    @Test
    public void getHeaders() {
        Response response = Response.response();
        assertNotNull(response);
        assertEquals(0, response.getHeaders().size());
        Response response1 = response.withHeader("header", "value");
        assertEquals(1, response1.getHeaders().size());
    }

    @Test
    public void getDirection() {
        Response response = Response.response();
        assertNotNull(response);
        assertEquals(Directions.noView(), response.getDirection());
        Response response1 = response.withView(Directions.view("/path"));
        assertNotEquals(Directions.noView(), response1.getDirection());
        assertEquals(Directions.view("/path"), response1.getDirection());
    }

    @Test
    public void getModel() {
        Response response = Response.response();
        assertNotNull(response);
        assertEquals(0, response.getModel().size());
        Response response1 = response.withModel("name","value");
        assertEquals(1, response1.getModel().size());
    }

    @Test
    public void getSession() {
        Response response = Response.response();
        assertNotNull(response);
        assertEquals(0, response.getSession().size());
        Response response1 = response.withSession("name","value");
        assertEquals(1, response1.getSession().size());
    }

    @Test
    public void getFlash() {
        Response response = Response.response();
        assertNotNull(response);
        assertNull(response.getFlash());
        Response response1 = response.withFlash("message");
        assertNotNull(response1.getFlash());
    }

    @Test
    public void doInvalidateSession() {
        Response response = Response.response();
        assertNotNull(response);
        assertFalse(response.doInvalidateSession());
        Response response1 = response.invalidateSession();
        assertTrue(response1.doInvalidateSession());
    }
}
