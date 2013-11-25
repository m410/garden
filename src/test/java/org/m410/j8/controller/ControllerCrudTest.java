package org.m410.j8.controller;

import com.google.common.collect.ImmutableSortedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.action.ActionRequest;
import org.m410.j8.action.RequestProperties;
import org.m410.j8.action.UserPrincipal;
import org.m410.j8.sample.MyController;
import org.m410.j8.sample.MyService;
import org.m410.j8.sample.MyServiceDaoImpl;
import org.m410.j8.sample.MyServiceImpl;

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
        @Override public UserPrincipal userPrincipal() { return null; }
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
        ActionResponse response = controller.httpGetAction.action(mockRequest);
        assertNotNull(response);
        assertNotNull(response.getModel());
        assertEquals("got id: 10", response.getModel().get("name"));
    }

    @Test
    public void testList() {
        MyService myService = new MyServiceImpl(new MyServiceDaoImpl());
        MyController controller = new MyController(myService);
        ActionResponse response = controller.httpGetAction.action(mockRequest);
        assertNotNull(response);
        assertNotNull(response.getModel());
        assertEquals("got id: 10", response.getModel().get("name"));
    }

    @Test
    public void testCreate() {
        MyService myService = new MyServiceImpl(new MyServiceDaoImpl());
        MyController controller = new MyController(myService);
        ActionResponse response = controller.httpPostAction.action(mockRequest);
        assertNotNull(response);
        assertNotNull(response.getModel());
        assertEquals("got id: 10", response.getModel().get("name"));
    }

    @Test
    public void testUpdate() {
        MyService myService = new MyServiceImpl(new MyServiceDaoImpl());
        MyController controller = new MyController(myService);
        ActionResponse response = controller.httpPutAction.action(mockRequest);
        assertNotNull(response);
        assertNotNull(response.getModel());
        assertEquals("got id: 10", response.getModel().get("name"));
    }

    @Test
    public void testDelete() {
        MyService myService = new MyServiceImpl(new MyServiceDaoImpl());
        MyController controller = new MyController(myService);
        ActionResponse response = controller.httpDeleteAction.action(mockRequest);
        assertNotNull(response);
        assertNotNull(response.getModel());
        assertEquals("got id: 10", response.getModel().get("name"));
    }
}
