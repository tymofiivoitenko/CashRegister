package servlets.receipt;

import dao.product.MysqlProductDaoImpl;
import dao.product.ProductDao;
import dao.receipt.MysqlReceiptDaoImpl;
import dao.receipt.ReceiptDao;
import model.Item;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/cart")
public class ReceiptServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductDao productDao;
    private ReceiptDao receiptDao;

    public ReceiptServlet() {
        super();
    }

    public void init() {
        productDao = new MysqlProductDaoImpl();
        receiptDao = new MysqlReceiptDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            doGet_DisplayCart(request, response);
        } else {
            if (action.equalsIgnoreCase("buy")) {
                doGet_Buy(request, response);
            } else if (action.equalsIgnoreCase("remove")) {
                doGet_Remove(request, response);
            }
        }
    }

    protected void doGet_DisplayCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        request.getRequestDispatcher("/WEB-INF/views/cart/cartView.jsp").forward(request, response);
    }

    protected void doGet_Remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();



        List<Item> cart = (List<Item>) session.getAttribute("cart");
        int index = isExisting(request.getParameter("id"), cart);

        cart.remove(index);

        receiptDao.deleteItem(index);
        session.setAttribute("cart", cart);
        response.sendRedirect("cart");
    }

    protected void doGet_Buy(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int productId = Integer.parseInt(request.getParameter("id"));
        Product productToBuy = productDao.selectProduct(productId);

        if (session.getAttribute("receiptId") == null) {
            System.out.println("receipt wasn't created yet, creating it....");
            int receiptId = receiptDao.createReceipt(666);
            session.setAttribute("receiptId", receiptId);
        }

        System.out.println("receipt exists, adding items to receipt ....");

        // Add item to receipt
        int receiptId = (Integer) session.getAttribute("receiptId");
        receiptDao.addItemToReceipt(receiptId, new Item(productToBuy, 1));

        // Receive all items for given receiptId from database
        List<Item> receipt = receiptDao.getAllItemsForReceipt(receiptId);
        session.setAttribute("cart", receipt);

        response.sendRedirect("/cart");
    }

    private int isExisting(String id, List<Item> cart) {
        for (int i = 0; i < cart.size(); i++) {
            // if (cart.get(i).getProduct().getId().equalsIgnoreCase(id)) {
            return i;
            //  }
        }
        return -1;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("HEY!!!!!!!!");
        HttpSession session = request.getSession();
        receiptDao.closeReceipt((Integer) session.getAttribute("receiptId"));

        session.setAttribute("cart", null);
        session.setAttribute("receiptId", null);
        response.sendRedirect("/catalog");
    }

}