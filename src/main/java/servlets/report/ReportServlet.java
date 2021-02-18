package servlets.report;

import dao.product.MysqlProductDaoImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reports")
public class ReportServlet extends HttpServlet {
    private MysqlProductDaoImpl productDao;
    private static final Logger LOGGER = Logger.getLogger(ReportServlet.class);

    public void init() {
        productDao = new MysqlProductDaoImpl();
    }

    public ReportServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Processing get request");
        ;
        request.getRequestDispatcher("/WEB-INF/views/report/reportView.jsp").include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Processing post request");
    }

}
