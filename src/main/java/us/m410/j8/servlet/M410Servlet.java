package us.m410.j8.servlet;

import us.m410.j8.application.Application;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class M410Servlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ((Application) request.getServletContext().getAttribute("application"))
                .doRequest(request, response);
    }
}
