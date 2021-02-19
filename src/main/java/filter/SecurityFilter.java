package filter;

import bean.CashBox;
import bean.User;
import org.apache.log4j.Logger;
import service.cashbox.CashBoxService;
import service.cashbox.CashBoxServiceImpl;
import utils.AppUtils;
import utils.SecurityUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class);
    private CashBoxService cashBoxService;

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        cashBoxService = new CashBoxServiceImpl();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        LOGGER.info("Processing doFilter request. Invoked for page: <" + request.getServletPath() + ">");

        String servletPath = request.getServletPath();

        // User information stored in the Session.
        // (After successful login).
        User loginedUser = (User) request.getSession().getAttribute("loginedUser");

        if (servletPath.equals("/login")) {
            try {
                chain.doFilter(request, response);
            } catch (NumberFormatException ex) {
                LOGGER.error(ex.getMessage());
                ex.printStackTrace();
            }

            return;
        }

        if (loginedUser != null) {
            LOGGER.info("User is not null. His role is: " + loginedUser.getRole());
            if (loginedUser.getRole().equals("CASHIER")) {
                LOGGER.info("Logined user is cashier. Check is cashier has active cashbox..");

                CashBox cashBox = cashBoxService.getActiveCashBox(loginedUser.getId());
                request.getSession().setAttribute("cashBox", cashBox);
            }
        }

        // Pages must be signed in
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
                response.sendRedirect(request.getContextPath() + "/login?redirectId=" + redirectId);
                return;
            }

            // Get user Role
            String role = loginedUser.getRole();

            // Check if the user has a valid role?
            boolean hasPermission = SecurityUtils.hasPermission(request, role);
            if (!hasPermission) {
                LOGGER.info("User doesn't have permission for this page. Forward to 'Access denied' page....");

                request.getRequestDispatcher("/WEB-INF/views/error/accessDeniedExceptionView.jsp")
                        .forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

}