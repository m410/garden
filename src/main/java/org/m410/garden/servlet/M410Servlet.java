package org.m410.garden.servlet;

import org.m410.garden.application.Application;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Default servlet for calling actions.
 *
 * @author Michael Fortin
 */
public class M410Servlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ((Application) request.getServletContext().getAttribute("application"))
                    .doRequest(request, response);
        }
        catch(Exception e) {
            throw new ServletException(e);
        }
    }
}
