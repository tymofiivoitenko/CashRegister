package servlets.products;

import model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/check")
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public CartServlet() {
        super();
    }

//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String action = request.getParameter("action");
//        if (action == null) {
//            doGet_DisplayCart(request, response);
//        } else {
//            if (action.equalsIgnoreCase("buy")) {
//                doGet_Buy(request, response);
//            } else if (action.equalsIgnoreCase("remove")) {
//                doGet_Remove(request, response);
//            }
//        }
//    }

    protected void doGet_DisplayCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("cart/productList.jsp").forward(request, response);
    }

//    protected void doGet_Remove(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        List<Item> cart = (List<Item>) session.getAttribute("cart");
//        int index = isExisting(request.getParameter("id"), cart);
//        cart.remove(index);
//        session.setAttribute("cart", cart);
//        response.sendRedirect("cart");
//    }

//    protected void doGet_Buy(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        ProductModel productModel = new ProductModel();
//        HttpSession session = request.getSession();
//        if (session.getAttribute("cart") == null) {
//            List<Item> cart = new ArrayList<Item>();
//            cart.add(new Item(productModel.find(request.getParameter("id")), 1));
//            session.setAttribute("cart", cart);
//        } else {
//            List<Item> cart = (List<Item>) session.getAttribute("cart");
//            int index = isExisting(request.getParameter("id"), cart);
//            if (index == -1) {
//                cart.add(new Item(productModel.find(request.getParameter("id")), 1));
//            } else {
//                int quantity = cart.get(index).getQuantity() + 1;
//                cart.get(index).setQuantity(quantity);
//            }
//            session.setAttribute("cart", cart);
//        }
//        response.sendRedirect("cart");
//    }

//    private int isExisting(String id, List<Item> cart) {
//        for (int i = 0; i < cart.size(); i++) {
//            if (cart.get(i).getProduct().getId().equalsIgnoreCase(id)) {
//                return i;
//            }
//        }
//        return -1;
//    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
