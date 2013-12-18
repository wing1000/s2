package fengfei.performance.jdbcteset;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dao {

    private static final Logger logger = LoggerFactory.getLogger(Dao.class);
    private Connection connection;

    public Dao(Connection connection) {
        this.connection = connection;
    }

    public int create(long nodeId) {
        int result = 0;
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            result = stmt.executeUpdate("INSERT INTO forward_1_edges"
                    + "(source_id, destination_id, edge_type, state, created_at, updated_at, "
                    + "valid_time, expired_at)" + " VALUES (" + nodeId + "," + nodeId
                    + ", 'T', '0', '1', '2', '0', '1')");

        } catch (Exception e) {
            logger.error("DAO exception", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
        }
        return result;
    }

    public String query() {
        String result = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select 1");
            rs.next();

            result = rs.getString(1);
        } catch (Exception e) {
            logger.error("DAO exception", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
        }
        return result;
    }
}
