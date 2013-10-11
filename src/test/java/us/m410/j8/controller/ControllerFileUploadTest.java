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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class ControllerFileUploadTest {
    FileUploadController controller = new FileUploadController();
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
    public void uploadFile() {
        final String expected = "post text";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/file");
        request.setMethod("POST");
        request.setContent(expected.getBytes());
        MockHttpServletResponse response = new MockHttpServletResponse();

        myApp.doRequest(request, response);
        assertEquals("text/plain", response.getContentType());
        assertEquals(expected, new String(response.getContentAsByteArray()));
    }
}
