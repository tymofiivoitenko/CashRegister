package servlets.login;

import bean.UserAccount;
import dao.user.UserDao;
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

    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/views/loginView.jsp");

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        // Find user in database
        UserAccount userAccount = UserDao.findUser(userName, password);

        System.out.println("====my user found===");
        System.out.println(userAccount);

        // Validate user by it username and password
        if (userAccount == null) {
            String errorMessage = "Invalid username or password";
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/views/loginView.jsp");

            dispatcher.forward(request, response);
            return;
        }

        // Store user info in Session.
        request.getSession().setAttribute("loginedUser", userAccount);

        // Redirect user to page, he wanted to access before login
        if (request.getParameter("redirectId") != null) {
            System.out.println("PArAaMETER: " + request.getParameter("redirectId"));
            int redirectId = Integer.parseInt(request.getParameter("redirectId"));

            String requestUri = AppUtils.getRedirectAfterLoginUrl(request.getSession(), redirectId);
            System.out.println("req uri: " + requestUri);

            if (requestUri != null) {
                System.out.println("req uri isn't null");
                response.sendRedirect(requestUri);
            }
        } else {
            System.out.println("req uri is null");
            // Default after successful login
            // redirect to /userInfo page
            response.sendRedirect(request.getContextPath() + "/userInfo");
        }
    }
}
