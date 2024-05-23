package ClothingStoreGUI;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;  // used to get connection
import java.sql.DatabaseMetaData;  // used to check if table exists
import java.sql.ResultSet;  // result set stores the result of a database query
import java.sql.SQLException;

/**
 *
 * DATABASE:
 * - Loads database
 * - Carries out database operations in SQL
 * 
 */

public class Database {

    // database connection data
    Connection conn = null;
    String url = "jdbc:derby:ClothingStoreDB;create=true";  // URL of the DB host
    String dbusername = "pdc";  // DB username
    String dbpassword = "pdc";  // DB password
    
    public void setup() {
        
        Connection conn = null;
        Statement stmt = null;
        
        // ENSURE TABLES ARE PRESENT IN DATABASE
        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            stmt = conn.createStatement();
            
            // Create tables if they don't already exist
            if (!tableExists(conn, "products")) {
                // !! TODO: setup product table properly with correct attributes
                stmt.executeUpdate("CREATE TABLE products (name VARCHAR(64))");
                stmt.executeUpdate("INSERT INTO products (product_id) VALUES 'Shoes' ");
            }
        // CATCH ERRORS
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("Error setting up database.");
        // CLOSE RESOURCES (such as connection and statement)
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    public boolean tableExists(Connection conn, String tableName) throws SQLException {
        // Search for table in metadata
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet rs = metaData.getTables(null, null, tableName.toUpperCase(), null)) {
            // If the result set has at least one row, the table exists
            return rs.next();
        }
    }
    
    
    
}
