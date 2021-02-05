package servlets.products;

import dao.ProductDao;
import model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products/new")
public class AddProductServlet extends HttpServlet {
    private ProductDao productDao;

    public void init() {
        productDao = new ProductDao();
    }

    public AddProductServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DoGet cp");
        request.getRequestDispatcher("/views/products/add/addProduct.jsp").include(request, response);
        System.out.println("s");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("productName");
        Integer quantity = Integer.valueOf(request.getParameter("quantity"));
        double price = Double.valueOf(request.getParameter("price"));

        if (productName.isEmpty()) {
            RequestDispatcher req = request.getRequestDispatcher("/views/products/add/addCreated.jsp");
            req.include(request, response);
        } else {
            System.out.println(productName);
            System.out.println(quantity);
            System.out.println("lets create");

            productDao.insertProduct(new Product(productName, price, quantity));

            RequestDispatcher req = request.getRequestDispatcher("/views/products/add/addCreated.jsp");
            req.forward(request, response);
        }

    }

}
