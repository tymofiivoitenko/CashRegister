package servlets.receipt;

import dao.receipt.MysqlReceiptDaoImpl;
import dao.receipt.ReceiptDao;
import dao.vigil.MysqlVigilDaoImpl;
import dao.vigil.VigilDao;
import model.Receipt;
import model.Vigil;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/all-receipts")
public class AllReceiptsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AllReceiptsServlet.class);

    private ReceiptDao receiptDao;
    private VigilDao vigilDao;

    public AllReceiptsServlet() {
        super();
    }

    public void init() {
        receiptDao = new MysqlReceiptDaoImpl();
        vigilDao = new MysqlVigilDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Processing get request");

        // Get all receipts from database
        List<Receipt> receipts = receiptDao.findAll();

        Vigil cashierVigil = (Vigil) request.getAttribute("vigil");
        LOGGER.info("Current User Vigil: " + cashierVigil);

        // Set attributes
        request.setAttribute("receipts", receipts);
        request.setAttribute("cashierVigil", cashierVigil);

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/receipt/allReceiptView.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Processing post request");

        // Start new vigil
        vigilDao.startVigil(777);

        // Set cashier vigil in attribute
        request.setAttribute("cashierVigil", vigilDao.getActiveVigil(777));

        request.getRequestDispatcher("/WEB-INF/views/receipt/allReceiptView.jsp").include(request, response);
    }

}
