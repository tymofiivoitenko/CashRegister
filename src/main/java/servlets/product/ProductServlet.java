package servlets.product;

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

@WebServlet({"/products", "/catalog"})
public class ProductServlet extends HttpServlet {
    private static final int defaultFirstPageNumber = 1;
    private static final int defaultNumberOfProductsOnPage = 5;

    private MysqlProductDaoImpl productDao;
    private static final Logger LOGGER = Logger.getLogger(ProductServlet.class);

    public void init() {
        productDao = new MysqlProductDaoImpl();
    }

    public ProductServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        LOGGER.info("Processing get request. Servlet path");

        // Getting parameters
        String paramPage = request.getParameter("page");
        String paramPageSize = request.getParameter("pageSize");

        // Set default parameters, if that was first call
        if (paramPage == null || paramPageSize == null) {
            paramPage = String.valueOf(defaultFirstPageNumber);
            paramPageSize = String.valueOf(defaultNumberOfProductsOnPage);
        }

        // Cast parameters to int
        int page = Integer.parseInt(paramPage);
        int pageSize = Integer.parseInt(paramPageSize);

        // Get limited number of products (pageSize) for given page
        List<Product> products = productDao.findProducts(page, pageSize);

        // Calculating max number of pages
        int productsNumber = productDao.getProductsNumber();
        int maxPage = (int) Math.ceil((double) productsNumber / pageSize);

        // Setting attributes
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("maxPage", maxPage);
        request.setAttribute("products", products);

        if (servletPath.equals("/catalog")) {
            LOGGER.info("Go to /catalog page");
            request.getRequestDispatcher("/WEB-INF/views/catalog/catalogView.jsp").forward(request, response);
        }

        if (servletPath.equals("/products")) {
            LOGGER.info("Go to /products page");

            // Forward to productList
            request.getRequestDispatcher("/WEB-INF/views/products/productList.jsp").forward(request, response);
        }

    }
}

