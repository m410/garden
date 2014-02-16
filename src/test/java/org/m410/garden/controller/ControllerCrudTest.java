package org.m410.garden.controller;

import com.google.common.collect.ImmutableSortedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.garden.controller.action.Identity;
import org.m410.garden.controller.action.http.ActionRequest;
import org.m410.garden.controller.action.http.RequestProperties;
import org.m410.garden.controller.action.http.Response;
import org.m410.garden.fixtures.MyController;
import org.m410.garden.fixtures.MyService;
import org.m410.garden.fixtures.MyServiceDaoImpl;
import org.m410.garden.fixtures.MyServiceImpl;

import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ControllerCrudTest {

    ActionRequest mockRequest = new ActionRequest() {
        @Override public boolean isActiveSession() { return false; }
        @Override public Identity identity() { return null; }
        @Override public Map<String, Object> session() { return null; }
        @Override public RequestProperties requestProperties() { return null; }
        @Override public Map<String, String> requestHeaders() { return null; }
        @Override public Map<String, String> urlParameters() { return ImmutableSortedMap.of("id", "10"); }
        @Override public Map<String, String[]> requestParameters() { return null; }
        @Override public InputStream postBodyAsStream() { return null; }
        @Override public String postBodyAsString() { return null; }
    };


    @Test
    public void testGetByUrlParameter() {
        MyService myService = new MyServiceImpl(new MyServiceDaoImpl());
        MyController controller = new MyController(myService);
        Response response = controller.httpGetAction.execute(mockRequest);

        assertNotNull(response);
        assertNotNull(response.getModel());
        assertEquals("got id: 10", response.getModel().get("name"));
    }

    @Test
    public void testList() {
        MyService myService = new MyServiceImpl(new MyServiceDaoImpl());
        MyController controller = new MyController(myService);

        Response response = controller.httpGetAction.execute(mockRequest);

        assertNotNull(response);
        assertNotNull(response.getModel());
        assertEquals("got id: 10", response.getModel().get("name"));
    }

    @Test
    public void testCreate() {
        MyService myService = new MyServiceImpl(new MyServiceDaoImpl());
        MyController controller = new MyController(myService);

        Response response = controller.httpPostAction.execute(mockRequest);

        assertNotNull(response);
        assertNotNull(response.getModel());
        assertEquals("got id: 10", response.getModel().get("name"));
    }

    @Test
    public void testUpdate() {
        MyService myService = new MyServiceImpl(new MyServiceDaoImpl());
        MyController controller = new MyController(myService);

        Response response = controller.httpPutAction.execute(mockRequest);

        assertNotNull(response);
        assertNotNull(response.getModel());
        assertEquals("got id: 10", response.getModel().get("name"));
    }

    @Test
    public void testDelete() {
        MyService myService = new MyServiceImpl(new MyServiceDaoImpl());
        MyController controller = new MyController(myService);

        Response response = controller.httpDeleteAction.execute(mockRequest);

        assertNotNull(response);
        assertNotNull(response.getModel());
        assertEquals("got id: 10", response.getModel().get("name"));
    }
}
