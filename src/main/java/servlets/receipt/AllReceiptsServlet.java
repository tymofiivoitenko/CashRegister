package servlets.receipt;

import dao.receipt.MysqlReceiptDaoImpl;
import dao.receipt.ReceiptDao;
import model.Receipt;
import org.apache.log4j.Logger;

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
    private static final int defaultFirstPageNumber = 1;
    private static final int defaultNumberOfReceiptsOnPage = 5;

    private ReceiptDao receiptDao;

    public AllReceiptsServlet() {
        super();
    }

    public void init() {
        receiptDao = new MysqlReceiptDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Processing get request");

        // Getting parameters
        String paramPage = request.getParameter("page");
        String paramPageSize = request.getParameter("pageSize");

        // Set default parameters, if that was first call
        if (paramPage == null || paramPageSize == null) {
            paramPage = String.valueOf(defaultFirstPageNumber);
            paramPageSize = String.valueOf(defaultNumberOfReceiptsOnPage);
        }

        // Cast parameters to int
        int page = Integer.parseInt(paramPage);
        int pageSize = Integer.parseInt(paramPageSize);

        // Get limited number of products (pageSize) for given page
        List<Receipt> receipts = receiptDao.findReceipts(page, pageSize);

        // Calculating max number of pages
        int productsNumber = receiptDao.getReceiptsNumber();
        int maxPage = (int) Math.ceil((double) productsNumber / pageSize);

        // Setting attributes
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("maxPage", maxPage);
        request.setAttribute("receipts", receipts);

        request.getRequestDispatcher("/WEB-INF/views/receipt/allReceiptView.jsp").forward(request, response);

    }

}
