package org.m410.garden.servlet;

import org.m410.garden.controller.action.http.HttpActionDefinition;
import org.m410.garden.controller.action.status.*;
import org.m410.garden.transaction.TransactionScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.m410.garden.application.Application;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Filter that routes action requests.
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

        final Optional<HttpActionDefinition> optionalAction = webapp.actionForRequest(request);

        log.debug("{}:{}[{}]",
                request.getMethod(),
                request.getRequestURI(),
                request.getContentType());

        if (optionalAction.isPresent()) {
            final HttpActionDefinition action = optionalAction.get();
            final ActionStatus status = action.status(request);

            switch (status.id()) {
                case ActionStatus.FORWARD:
                    log.debug("Forward({})", action);
                    String path = ((Forward)status).getPath();
                    request.getRequestDispatcher(path).forward(request,response);
                    break;

                case ActionStatus.ACT_ON:
                    log.debug("ActOn({},{})", action, action.getTransactionScope());

                    if(action.getTransactionScope() == TransactionScope.ActionAndView)
                        webapp.doWithThreadLocals(() -> {
                            wrapExceptions(() -> chain.doFilter(req, res));
                            return null;
                        });
                    else
                        chain.doFilter(req, res);

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

                default:
                    throw new RuntimeException("Unknown status:" + status);
            }
        }
        else {
            log.debug("NotAnAction({})", request.getRequestURI());
            chain.doFilter(req, res);
        }
    }

    /**
     * Used to rethrow caught exceptions to RuntimeException
     */
    interface Throwing {
        void doIn() throws Exception;
    }

    /**
     * Used to rethrow caught exceptions to RuntimeException
     * @param f a block that possible throws a caught exception
     */
    void wrapExceptions(Throwing f) {
        try {
            f.doIn();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
    }
}
