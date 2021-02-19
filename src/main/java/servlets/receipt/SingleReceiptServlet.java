package servlets.receipt;

import bean.ReceiptItem;
import org.apache.log4j.Logger;
import service.receipt.ReceiptService;
import service.receipt.ReceiptServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/receipt")
public class SingleReceiptServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(SingleReceiptServlet.class);

    private ReceiptService receiptService;

    public SingleReceiptServlet() {
        super();
    }

    public void init() {
        receiptService = new ReceiptServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.info("Processing get request");
        // Get action parameter
        String action = req.getParameter("action");
        LOGGER.info("Action: " + action);

        if (action == null) {

            int receiptId = (int) req.getSession().getAttribute("receiptId");
            List<ReceiptItem> receiptItems = receiptService.getReceiptItems(receiptId);

            req.setAttribute("receiptItems", receiptItems);

            // Go to receipt view page
            req.getRequestDispatcher("/WEB-INF/views/receipt/singleReceiptView.jsp").forward(req, resp);
            return;
        }

        if (action.equals("addItem")) {
            // Get product which need to be added into receipt
            int productId = Integer.parseInt(req.getParameter("id"));

            // Set receiptId in variable
            int receiptId = (int) req.getSession().getAttribute("receiptId");

            receiptService.addItem(receiptId, productId);

            // Display updated receipt page
            resp.sendRedirect("/receipt");
            return;
        }

        if (action.equals("changeItemQuantity")) {
            // Retrieving item id to be updated from receipt
            int itemToBeUpdated = Integer.parseInt(req.getParameter("id"));

            // Retrieving new number of items
            int newNumberOfItems = Integer.parseInt(req.getParameter("quantity"));
            receiptService.changeItemsQuantity(itemToBeUpdated, newNumberOfItems);

            // Display updated receipt page
            resp.sendRedirect("/receipt");
            return;
        }

        if (action.equals("removeItem")) {
            // Retrieving item id to be deleted from receipt
            int itemToBeRemovedFromReceipt = Integer.parseInt(req.getParameter("id"));

            receiptService.removeItem(itemToBeRemovedFromReceipt);

            // Display updated receipt page
            resp.sendRedirect("/receipt");
            return;
        }

        // Display updated receipt page
        resp.sendRedirect("/receipt");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOGGER.info("Processing post request");

        // Get action parameter
        String action = req.getParameter("action");
        LOGGER.info("Action: " + action);

        if (action.equals("createReceipt")) {
            if (req.getSession().getAttribute("cashBox") == null) {
                LOGGER.info("Cashier can't create new receipt. He doesn't have opened cashBox");

                req.getRequestDispatcher("/WEB-INF/views/error/noActiveCashBoxExceptionView.jsp")
                        .forward(req, resp);
                return;
            }
            receiptService.createReceipt(req, resp);
            return;
        }

        if (action.equals("closeReceipt")) {
            receiptService.closeReceipt(req, resp);
            return;
        }

        LOGGER.error("Unexpected action: " + action);
        throw new IllegalStateException("Unexpected action: " + action);
    }


}