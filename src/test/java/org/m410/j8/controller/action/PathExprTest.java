package org.m410.j8.controller.action;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.m410.j8.controller.action.http.HttpActionDefinition;
import org.m410.j8.application.MockController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Michael Fortin
 */
@RunWith(JUnit4.class)
public class PathExprTest {
    @Test
    public void testCompareToEmptyPath() {
        PathExpr path1 = new PathExpr("/");
        PathExpr path2 = new PathExpr("/");
        assertTrue(path1.compareTo(path2) == 0);
        assertTrue(path1.equals(path2));
    }

    @Test
    public void testCompareToLongPath() {
        PathExpr path1 = new PathExpr("/persons/{id}/address");
        PathExpr path2 = new PathExpr("/persons/{id}/address");
        assertTrue(path1.compareTo(path2) == 0);
        assertTrue(path1.equals(path2));
    }

    @Test
    public void testCompareToLongPathNotEqual() {
        PathExpr path1 = new PathExpr("/persons/{id}/address");
        PathExpr path2 = new PathExpr("/persons/{id}/addresses");
        assertFalse(path1.compareTo(path2) == 0);
        assertFalse(path1.equals(path2));
    }

    @Test
    public void testCombinePath() {
        String[] expected = new String[]{"persons","{id}","address"};
        PathExpr path = new PathExpr("persons/{id}").append("address");
        assertTrue(Arrays.equals(path.getTokens(), expected));
    }

    @Test
    public void testMatchWithWildcardRegex() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/persons/21/address");
        when(request.getMethod()).thenReturn("GET");

        PathExpr path = new PathExpr("persons/{id}/address");
        assertTrue(path.doesPathMatch(request));
    }

    @Test
    public void testMatchWithRegex() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/persons/21/address");
        when(request.getMethod()).thenReturn("GET");
        PathExpr path = new PathExpr("persons/{id:\\d+}/address");
        assertTrue(path.doesPathMatch(request));
    }

    @Test
    public void testMatchContext() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("/ctx");
        when(request.getRequestURI()).thenReturn("/persons/21/address");
        when(request.getMethod()).thenReturn("GET");
        PathExpr path = new PathExpr("persons/{id:\\d+}/address");
        assertTrue(path.doesPathMatch(request));
    }

    @Test
    public void testPullParamsFromURI() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("/ctx");
        when(request.getRequestURI()).thenReturn("/ctx/persons/21/address");
        when(request.getMethod()).thenReturn("GET");

        PathExpr path = new PathExpr("persons/{id:\\d+}/address");
        Map<String,String> params = path.parametersForRequest(request);
        assertEquals(1, params.size());
        assertEquals("21", params.get("id"));
    }

    @Test public void doesMockPathMatch() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getRequestURI()).thenReturn("/mock");
        when(request.getMethod()).thenReturn("GET");

        final MockController controller = new MockController();
        final HttpActionDefinition action = controller.actions().get(0);
        assertTrue(action.doesRequestMatchAction(request));
    }
}
