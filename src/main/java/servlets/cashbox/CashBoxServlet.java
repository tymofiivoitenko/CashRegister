package servlets.cashbox;

import bean.CashBox;
import bean.User;
import org.apache.log4j.Logger;
import service.cashbox.CashBoxService;
import service.cashbox.CashBoxServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cashbox")
public class CashBoxServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CashBoxServlet.class);
    private CashBoxService cashBoxService;

    public CashBoxServlet() {
        super();
    }

    public void init() {
        cashBoxService = new CashBoxServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Processing post request");

        String action = request.getParameter("action");
        LOGGER.info("Action: " + action);

        if (action.equals("start")) {
            LOGGER.info("Processing 'start cashBox' action...");

            // Get current logined user from session
            User loginedUser = (User) request.getSession().getAttribute("loginedUser");
            CashBox cashBox = cashBoxService.openCashBox(loginedUser.getId());
            request.getSession().setAttribute("cashBox", cashBox);

            response.sendRedirect(request.getContextPath() + "/all-receipts");
            return;
        }

        if (action.equals("finish")) {
            LOGGER.info("Processing 'finish cashBox' action...");
            CashBox cashBox = (CashBox) request.getSession().getAttribute("cashBox");

            // Nullifying cashBox attribute in this session
            request.getSession().setAttribute("cashBox", null);

            // Close given cashBox
            cashBoxService.closeCashBox(cashBox.getId());

            // Go to add new receipts
            response.sendRedirect(request.getContextPath() + "/all-receipts");
            return;
        }

        LOGGER.error("Unexpected cashBox action: " + action);
    }
}
