package servlets.catalog;

import dao.product.MysqlProductDaoImpl;
import dao.product.ProductDao;
import model.Product;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CatalogServlet.class);
    private ProductDao productDao;

    public CatalogServlet() {
        super();
    }

    public void init() {
        productDao = new MysqlProductDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Processing get request");

        List<Product> products = productDao.findAll();

        request.setAttribute("products", products);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/catalog/catalogView.jsp");
        dispatcher.forward(request, response);
    }

}

