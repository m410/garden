package us.m410.j8.controller;

import com.google.common.collect.ImmutableSortedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import us.m410.j8.action.ActionRequest;
import us.m410.j8.action.ActionResponse;
import us.m410.j8.action.UserPrincipal;
import us.m410.j8.sample.MyController;
import us.m410.j8.sample.MyService;
import us.m410.j8.sample.MyServiceDaoImpl;
import us.m410.j8.sample.MyServiceImpl;

import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ControllerCrudTest {

    ActionRequest mockRequest = new ActionRequest() {
        @Override
        public boolean isActiveSession() {
            return false;
        }

        @Override
        public UserPrincipal userPrincipal() {
            return null;
        }

        @Override
        public Map<String, Object> session() {
            return null;
        }

        @Override
        public Map<String, String> requestProperties() {
            return null;
        }

        @Override
        public Map<String, String> requestHeaders() {
            return null;
        }

        @Override
        public Map<String, String> urlParameters() {
            return ImmutableSortedMap.of("id", "10");
        }

        @Override
        public Map<String, String[]> requestParameters() {
            return null;
        }

        @Override
        public InputStream postBodyAsStream() {
            return null;
        }

        @Override
        public String postBodyAsString() {
            return null;
        }
    };

    @Test
    public void testGetByUrlParameter() {
        MyService myService = new MyServiceImpl(new MyServiceDaoImpl());
        MyController controller = new MyController(myService);
        ActionResponse response = controller.myAction.action(mockRequest);
        assertNotNull(response);
        assertNotNull(response.getModel());
        assertEquals("got id: 10", response.getModel().get("name"));
    }

    @Test
    public void testList() {
        assertTrue("Implement me", false);
    }

    @Test
    public void testCreate() {
        assertTrue("Implement me", false);
    }

    @Test
    public void testUpdate() {
        assertTrue("Implement me", false);
    }

    @Test
    public void testDelete() {
        assertTrue("Implement me", false);
    }
}
