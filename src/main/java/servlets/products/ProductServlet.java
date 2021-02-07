package servlets.products;

import dao.product.MysqlProductDaoImpl;
import model.Product;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private MysqlProductDaoImpl productDao;
    private static final Logger LOGGER = Logger.getLogger(ProductServlet.class);

    public void init() {
        productDao = new MysqlProductDaoImpl();
    }
    public ProductServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Processing get request");

        List<Product> products = productDao.selectAllProducts();

        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/views/products/productList.jsp").include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Processing post request");
    }

}

