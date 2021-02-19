package servlets.login;

import bean.User;
import dao.user.MysqlUserDaoImpl;
import dao.user.UserDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import utils.AppUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);
    private UserDao userDao;

    public LoginServlet() {
        super();
    }

    public void init() {
        userDao = new MysqlUserDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing get request");
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/login/loginView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing post request");

        String userName = request.getParameter("userName");
        String password = DigestUtils.sha256Hex(request.getParameter("password"));

        LOGGER.info("Checking database for username: " + userName);
        // Lookup for user in database
        User user = userDao.findUser(userName, password);

        LOGGER.info("Found user: " + user + ". Checking if user is null....");

        // Validate user by it username and password
        if (user == null) {
            LOGGER.info("User is null, send error message ");

            String errorMessage = "Invalid username or password";
            request.setAttribute("errorMessage", errorMessage);

            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/login/loginView.jsp");

            dispatcher.forward(request, response);
            return;
        }

        // Store user info in Session.
        request.getSession().setAttribute("loginedUser", user);

        LOGGER.info("User isn't null, checking if there is need for redirecting user on another page ");

        // Redirect user to page, he wanted to access before login
        if (request.getParameter("redirectId") != null && request.getParameter("redirectId") != "") {

            int redirectId = Integer.parseInt(request.getParameter("redirectId"));
            String requestUri = AppUtils.getRedirectAfterLoginUrl(request.getSession(), redirectId);

            if (requestUri != null) {
                LOGGER.info("Redirect to: " + requestUri);
                response.sendRedirect(requestUri);
            }
        } else {
            LOGGER.info("No need in redirection, go to default HOME page");
            //  After successful login redirect to Default /home page
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
