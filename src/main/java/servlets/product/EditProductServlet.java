package servlets.product;

import dao.product.MysqlProductDaoImpl;
import bean.Product;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products/edit")
public class EditProductServlet extends HttpServlet {

    private MysqlProductDaoImpl productDao;
    private static final Logger LOGGER = Logger.getLogger(servlets.product.EditProductServlet.class);

    {
        productDao = new MysqlProductDaoImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("Processing get request");

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            String productName = request.getParameter("productName");
            double productPrice = Double.valueOf(request.getParameter("productPrice"));
            double productQuantity = Double.valueOf(request.getParameter("productQuantity"));
            String unit = request.getParameter("productUnit");

            productDao.updateProduct(new Product(productId, productName, productPrice, productQuantity, unit));

        } catch (Exception e) {
            LOGGER.info(e.getStackTrace());
            e.printStackTrace();
        }

        response.sendRedirect("/products");
    }
}
