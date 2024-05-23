package ClothingStoreGUI;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;  // used to get connection
import java.sql.DatabaseMetaData;  // used to check if table exists
import java.sql.ResultSet;  // result set stores the result of a database query
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * DATABASE: - Loads database - Carries out database operations in SQL
 *
 */
public class Database {

    // database connection data
    Connection conn = null;
    String url = "jdbc:derby:ClothingStoreDB;create=true";  // URL of the DB host
    String dbusername = "pdc";  // DB username
    String dbpassword = "pdc";  // DB password

    // Create initial tables if they don't exist
    public void setup() {
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            stmt = conn.createStatement();
            
            // Delete tables
//            DatabaseDefaultHandler defaultData = new DatabaseDefaultHandler();
//            defaultData.deleteTables(stmt);
            
            // Create tables & fill data if they don't already exist
            createTables(stmt);

        } catch (SQLException e) {
            System.out.println("Error setting up database: " + e.getMessage());
            System.out.println("There may be another instance running. Please only run one instance at a time.");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private void createTables(Statement stmt) throws SQLException {
        
        DatabaseDefaultHandler defaultData = new DatabaseDefaultHandler();
        
//        defaultData.createProductTable(stmt);
//        defaultData.createProductTypesTable(stmt);
//        defaultData.createCategoriesTable(stmt);
//        defaultData.createGendersTable(stmt);
//        defaultData.createDiscountsTable(stmt);
        
        // holds different products
        if (!tableExists("products")) {
            defaultData.createProductTable(stmt);
        }

        // holds product types (clothing, shoe)
        if (!tableExists("product_types")) {
            defaultData.createProductTypesTable(stmt);
        }

        // holds product categories (casual, formal...)
        if (!tableExists("categories")) {
            defaultData.createCategoriesTable(stmt);
        }

        // holds product gender (male, female, unisex)
        if (!tableExists("genders")) {
            defaultData.createGendersTable(stmt);
        }

        // holds discount types (fixed, percent)
        if (!tableExists("discounts")) {
            defaultData.createDiscountsTable(stmt);
        }
    }
    
    public void previewTable(String tableName) {
        String query = "SELECT * FROM " + tableName;

        try ( Connection con = DriverManager.getConnection(url, dbusername, dbpassword);  Statement stmt = con.createStatement();  ResultSet rs = stmt.executeQuery(query)) {

            // Get metadata to dynamically handle any table structure
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + "\t");
            }
            System.out.println();

            // Print rows
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // check if a table exists in the database
    public boolean tableExists(String tableName) {
        try {
            // Search for table in metadata
            DatabaseMetaData metaData = conn.getMetaData();
            try ( ResultSet rs = metaData.getTables(null, null, tableName.toUpperCase(), null)) {
                // If the result set has at least one row, the table exists
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    // used for debugging
//    public void getTable(String str) {
//        
//        // Query data
//        try (Statement stmt = conn.createStatement()) {
//            String querySQL = "SELECT * FROM " + str;
//            ResultSet rs = stmt.executeQuery(querySQL);
//            System.out.println("Query results:");
//
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                double price = rs.getDouble("price");
//                int quantity = rs.getInt("quantity");
//                System.out.println("ID: " + id + ", Name: " + name + ", Price: " + price + ", Quantity: " + quantity);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        
//    }
}
