package filter;

import bean.UserAccount;
import org.apache.log4j.Logger;
import request.UserRoleRequestWrapper;
import utils.AppUtils;
import utils.SecurityUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class SecurityFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class);

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        LOGGER.info("Processing doFilter request");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String servletPath = request.getServletPath();

        // User information stored in the Session.
        // (After successful login).
        UserAccount loginedUser = (UserAccount) request.getSession().getAttribute("loginedUser");

        if (servletPath.equals("/login")) {
            try {
                chain.doFilter(request, response);
            } catch (NumberFormatException ex) {
                LOGGER.error(ex.getMessage());
                ex.printStackTrace();
            }

            return;
        }

        HttpServletRequest wrapRequest = request;

        if (loginedUser != null) {
            LOGGER.info("LoginedUser is not null");

            // Get user Name
            String userName = loginedUser.getUserName();

            // Get user Roles
            List<String> roles = loginedUser.getRoles();

            // Wrap old request by a new Request with userName and Roles information.
            wrapRequest = new UserRoleRequestWrapper(userName, roles, request);
        }

        // Pages must be signed in.
        if (SecurityUtils.isSecurityPage(request)) {
            LOGGER.info("This page is secured: " + request.toString());

            // If the user is not logged in,
            // Redirect to the login page.
            if (loginedUser == null) {
                LOGGER.info("User isn't loggedIn. Redirecting on login page....");

                // Take requested URI
                String requestUri = request.getRequestURI();

                // Store the current page to redirect to after successful login.
                int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);

                // Redirect
                response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
                return;
            }

            // Check if the user has a valid role?
            boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
            if (!hasPermission) {
                LOGGER.info("User doesn't have permission for this page. Forward to 'Access denied' page....");
                RequestDispatcher dispatcher //
                        = request.getServletContext().getRequestDispatcher("/views/accessDeniedView.jsp");

                dispatcher.forward(request, response);
                return;
            }
        }

        chain.doFilter(wrapRequest, response);
    }

}
