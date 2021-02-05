package connection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import javax.naming.Context;
import javax.sql.DataSource;
import java.sql.SQLException;

public class ConnectionPool {
    private static ConnectionPool connectionPool;

    private ConnectionPool() {
    }

    public static synchronized ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public Connection getConnection() {
        Context ctx;
        Connection c = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CashRegisterDB");
            c = ds.getConnection();
        } catch (NamingException e) {
            //LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            //LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return c;

    }

    public void commitAndClose(Connection con) {
        try {
            con.commit();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
