package us.m410.j8.controller;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import us.m410.j8.configuration.Configuration;
import us.m410.j8.sample.MyWebApp;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ControllerXmlTest {

    XmlController controller = new XmlController();
    MyWebApp myApp;

    @Before
    public void setup() {
        myApp = new MyWebApp(null) {
            @Override public List<? extends Controller> makeControllers(Configuration c) {
                return ImmutableList.of(controller);
            }
        };
        myApp.onStartup();
    }

    @Test
    public void testGetXml() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/xml");
        request.setMethod("GET");
        MockHttpServletResponse response = new MockHttpServletResponse();

        myApp.doRequest(request, response);
        assertEquals("application/xml",response.getContentType());
    }

    @Test
    public void testPostXml() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/xml");
        request.setMethod("POST");
        request.setContent("<f></f>".getBytes());
        MockHttpServletResponse response = new MockHttpServletResponse();

        assertNotNull(request.getInputStream());
        myApp.doRequest(request, response);
        assertEquals("application/xml", response.getContentType());
    }

    @Test
    public void testPutXml() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/xml/100");
        request.setMethod("PUT");
        request.setContent("<f></f>".getBytes());
        MockHttpServletResponse response = new MockHttpServletResponse();

        myApp.doRequest(request, response);
        assertEquals("application/xml", response.getContentType());
    }

    @Test
    public void testDeleteXml() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/xml/100");
        request.setMethod("DELETE");
        MockHttpServletResponse response = new MockHttpServletResponse();

        myApp.doRequest(request, response);
//        assertEquals("application/xml", response.get);
    }
}
