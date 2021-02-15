package servlets.product;

import dao.product.MysqlProductDaoImpl;
import model.Product;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products/new")
public class AddProductServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AddProductServlet.class);
    private MysqlProductDaoImpl productDao;

    public void init() {
        productDao = new MysqlProductDaoImpl();
    }

    public AddProductServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Processing post request");

        try {
            String productName = request.getParameter("productName");
            Integer quantity = Integer.valueOf(request.getParameter("productQuantity"));
            double price = Double.valueOf(request.getParameter("productPrice"));
            String unit = request.getParameter("productUnit");

            productDao.insertProduct(new Product(productName, price, quantity, unit));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();

            RequestDispatcher req = request.getRequestDispatcher("/WEB-INF/views/products/add/failedToCreateProduct.jsp");
            req.include(request, response);
        }

        response.sendRedirect("/products");
    }

}
