package servlets.vigil;

import dao.vigil.MysqlVigilDaoImpl;
import dao.vigil.VigilDao;
import model.User;
import model.Vigil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vigil")
public class VigilServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(VigilServlet.class);

    private VigilDao vigilDao;

    public VigilServlet() {
        super();
    }

    public void init() {
        vigilDao = new MysqlVigilDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Processing get request");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Processing post request");
        LOGGER.info("===================");

        // Get current logined user from session
        User loginedUser = (User) request.getSession().getAttribute("loginedUser");
        Vigil vigil = vigilDao.getActiveVigil(loginedUser.getId());
        LOGGER.info("Active vigil for user: <" + loginedUser.getId() + "> is: " + vigil);

        if (vigil != null) {
            LOGGER.info("User <" + loginedUser.getUserName() + "> already has active vigil: <" + vigil.getId() + "> stop it");

            // Finish user vigil
            vigilDao.finishVigil(vigil.getId());

            // Nullify vigil
            request.getSession().setAttribute("vigil", null);

            //request.getRequestDispatcher("/").forward(request, response);
            response.sendRedirect(request.getContextPath() + "/");
            LOGGER.info("we continue..");
            return;
        }
        LOGGER.info("There is no active vigil for user: " + loginedUser.getUserName());

        // Start new vigil
        vigilDao.startVigil(loginedUser.getId());

        vigil = vigilDao.getActiveVigil(loginedUser.getId());
        LOGGER.info("New vigil id: <" + vigil.getId() + ">");

        // Set cashier vigil in attribute
        request.getSession().setAttribute("vigil", vigil);

        //request.getRequestDispatcher("/all-receipts").forward(request, response);
        response.sendRedirect(request.getContextPath() + "/all-receipts");
    }
}
