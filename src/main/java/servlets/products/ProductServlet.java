package servlets.products;

import dao.ProductDao;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private ProductDao productDao;

    public void init() {
        productDao = new ProductDao();
    }
    public ProductServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Products DoGet");

        List<Product> products = productDao.selectAllProducts();

        request.setAttribute("products", products);
        request.getRequestDispatcher("/views/products/productList.jsp").include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Products DoPost");
    }

}

