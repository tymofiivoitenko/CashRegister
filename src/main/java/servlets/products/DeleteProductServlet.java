package servlets.products;

import dao.product.MysqlProductDaoImpl;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products/delete")
public class DeleteProductServlet extends HttpServlet {
    private MysqlProductDaoImpl productDao;
    private static final Logger LOGGER = Logger.getLogger(DeleteProductServlet.class);

    {
        productDao = new MysqlProductDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Processing get request");

        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            productDao.deleteProduct(productId);
        } catch (Exception e) {
            LOGGER.info(e.getStackTrace());
            e.printStackTrace();

            RequestDispatcher req = request.getRequestDispatcher("/views/products/add/failedToCreateProduct.jsp");
            req.include(request, response);
        }

        response.sendRedirect("/products");
    }
}
