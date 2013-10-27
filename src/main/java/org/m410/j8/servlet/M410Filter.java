package org.m410.j8.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.m410.j8.action.*;
import org.m410.j8.action.status.ActionStatus;
import org.m410.j8.action.status.DispatchTo;
import org.m410.j8.action.status.RedirectToSecure;
import org.m410.j8.action.status.RedirectToAuth;
import org.m410.j8.application.Application;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Document Me..
 *
 * @author Michael Fortin
 */
public class M410Filter implements Filter {
    private static Logger log = LoggerFactory.getLogger(M410Filter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final Application webapp = (Application) request.getServletContext().getAttribute("application");

        final Optional<ActionDefinition> optionalAction = webapp.actionForRequest(request);

        if (optionalAction.isPresent()) {
            final ActionDefinition action = optionalAction.get();
            final ActionStatus status = action.status(request);

            switch (status.id()) {
                case ActionStatus.ACT_ON:
                    log.debug("ActOn({})", action);
                    webapp.doWithThreadLocals(() -> {
                        chain.doFilter(req, res);
                    });
                    break;
                case ActionStatus.ACT_ON_ASYNC:
                    log.debug("ActOnAsync({})", action);
                    chain.doFilter(req, res);
                    break;
                case ActionStatus.REDIRECT_TO_SECURE:
                    final String path1 = ((RedirectToSecure) status).getPath();
                    log.debug("RedirectToSecure({})", path1);
                    response.sendRedirect(path1);
                    break;
                case ActionStatus.REDIRECT_TO_AUTH:
                    RedirectToAuth redirectAuth = (RedirectToAuth) status;
                    log.debug("RedirectToAuthenticate({},{})", redirectAuth.getPath(), redirectAuth.getLastView());
                    request.getSession().setAttribute("last_view", redirectAuth.getLastView());
                    response.sendRedirect(redirectAuth.getPath());
                    break;
                case ActionStatus.DISPATCH_TO:
                    final String path2 = ((DispatchTo) status).getPath();
                    log.debug("DispatchTo({})", path2);
                    req.getRequestDispatcher(path2).forward(req, res);
                    break;
                case ActionStatus.FORBIDDEN:
                    log.debug("Forbidden({})", request.getRequestURI());
                    response.sendError(403, "Forbidden, Not Authorized to view this resource");
                    break;
            }
        }
        else {
            log.trace("NotAnAction({})", request.getRequestURI());
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
