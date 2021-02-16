package servlets.receipt;

import dao.product.MysqlProductDaoImpl;
import dao.product.ProductDao;
import dao.receipt.MysqlReceiptDaoImpl;
import dao.receipt.ReceiptDao;
import model.Item;
import model.Product;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/receipt")
public class ReceiptServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(ReceiptServlet.class);

    private ProductDao productDao;
    private ReceiptDao receiptDao;

    public ReceiptServlet() {
        super();
    }

    public void init() {
        productDao = new MysqlProductDaoImpl();
        receiptDao = new MysqlReceiptDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Processing get request");

        String action = request.getParameter("action");
        LOGGER.info("Action: " + action);

        if (action == null) {
            doGet_Display(request, response);
            return;
        }

        if (action.equalsIgnoreCase("buy")) {
            doGet_Buy(request, response);
            return;
        }

        if (action.equalsIgnoreCase("remove")) {
            doGet_Remove(request, response);
            return;
        }

        if (action.equalsIgnoreCase("applyChanges")) {
            doGet_Apply(request, response);
            return;
        }

        LOGGER.error("Unexpected action: " + action);
    }

    protected void doGet_Display(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Displaying current receipt");

        // Retrieve receipt id from session
        HttpSession session = request.getSession();
        int receiptId = (int) session.getAttribute("receiptId");

        // Retrieve all items for current receipt from database
        List<Item> receipt = receiptDao.getAllItemsForReceipt(receiptId);
        session.setAttribute("receipt", receipt);

        // Go to receipt view page
        request.getRequestDispatcher("/WEB-INF/views/receipt/receiptView.jsp").forward(request, response);
    }

    protected void doGet_Buy(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Processing <buy> action");

        // Get session
        HttpSession session = request.getSession();

        // Check if given session already contains open receipt
        // Otherwise create new one
        if (session.getAttribute("receiptId") == null) {
            int receiptId = receiptDao.createReceipt(666);
            session.setAttribute("receiptId", receiptId);
        }

        // Get product which need to be added into receipt
        int productId = Integer.parseInt(request.getParameter("id"));
        Product productToBuy = productDao.selectProduct(productId);

        // Set receiptId in variable
        int receiptId = (int) session.getAttribute("receiptId");

        // Check if item with such product already added in receipt
        Item item = receiptDao.getItemByProduct(receiptId, productToBuy.getId());
        if (item != null) {
            LOGGER.info("Item with such product already exist. Updating quantity of products");

            // Update Item Quantity in DB
            receiptDao.updateItemQuantity(item.getId(), item.getQuantity() + 1);
        } else {
            LOGGER.info("Item with such product doesn't. Adding new item in receipt");

            // Add item to receipt
            receiptDao.addItemToReceipt(receiptId, new Item(productToBuy, 1));
        }

        // Display updated receipt page
        response.sendRedirect("/receipt");
    }

    protected void doGet_Remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Removing item from receipt");

        // Retrieving item id to be deleted from receipt
        int itemToBeDeletedFromReceipt = Integer.parseInt(request.getParameter("id"));

        // Delete item from db
        receiptDao.deleteItem(itemToBeDeletedFromReceipt);

        // Display updated receipt page
        response.sendRedirect("/receipt");
    }

    protected void doGet_Apply(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Updating quantity of element");

        // Retrieving item id to be updated from receipt
        int itemToBeUpdated = Integer.parseInt(request.getParameter("id"));

        // Retrieving new number of items
        int newNumberOfItems = Integer.parseInt(request.getParameter("quantity"));

        // Update Item Quantity in DB
        receiptDao.updateItemQuantity(itemToBeUpdated, newNumberOfItems);

        // Display updated receipt page
        response.sendRedirect("/receipt");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Processing post request");

        // Get receipt ID, which will be completed
        HttpSession session = request.getSession();
        int receiptToBeCompleted = (Integer) session.getAttribute("receiptId");

        // Change status in Database from "Created" to "Completed"
        receiptDao.setReceiptStatus(receiptToBeCompleted, "COMPLETED");

        // Nullify current receipt in session
        session.setAttribute("receipt", null);
        session.setAttribute("receiptId", null);

        // Get back to catalog page
        response.sendRedirect("/catalog");
    }

}