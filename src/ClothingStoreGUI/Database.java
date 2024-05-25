package ClothingStoreGUI;

import ClothingStoreGUI.Enums.Category;
import ClothingStoreGUI.Enums.DiscountType;
import ClothingStoreGUI.Enums.Gender;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;  // used to get connection
import java.sql.DatabaseMetaData;  // used to check if table exists
import java.sql.ResultSet;  // result set stores the result of a database query
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

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

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, dbusername, dbpassword);
    }

    // Create initial tables if they don't exist
    public void setup() {
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            stmt = conn.createStatement();

            DatabaseDefaultHandler defaultData = new DatabaseDefaultHandler(this);

            // Delete tables
//             defaultData.deleteTables(stmt);
            // Create tables & fill data if they don't already exist
            defaultData.createTables(stmt);

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

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try ( Connection con = getConnection();  PreparedStatement pstmt = con.prepareStatement(sql);  ResultSet rs = pstmt.executeQuery()) {
            
//            + "available SMALLINT,"
//            + "id INT PRIMARY KEY,"
//            + "type INT NOT NULL,"
//            + "name VARCHAR(64) UNIQUE NOT NULL,"
//            + "category INT NOT NULL,"
//            + "price NUMERIC(6, 2) NOT NULL,"  // cap prices at 6 digits!
//            + "gender_id INT NOT NULL,"
//            + "discount_id INT,"
//            + "discount_amount NUMERIC(10, 2))");
            
            while (rs.next()) {
                boolean available = rs.getBoolean("available");
                int id = rs.getInt("id");
                int type = rs.getInt("type");
                String name = rs.getString("name");
                int category = rs.getInt("category");
                BigDecimal price = rs.getBigDecimal("price");
                int gender = rs.getInt("gender_id");
                int discount_type = rs.getInt("discount_id");
                BigDecimal discount_amount = rs.getBigDecimal("discount_amount");
                
                Discount discount = null;

                switch (DiscountType.intToDiscount(discount_type)) {
                    case NONE:  // None
                        break;
                    case FIXED:  // Fixed
                        discount = new FixedDiscount(discount_amount);
                    case PERCENT:  // Percent
                        discount = new PctDiscount(discount_amount);
                    default:
                        System.out.println("Unknown discount type in Database: " + String.valueOf(discount_type));
                }                
                
                Product product = null;
                
                switch (type) {
                    case 1:  // Clothing
//                        String name, boolean available, double price, int gender, int category, Discount discount
                        product = new ClothingItem(id, name, available, price, Gender.intToGender(gender), Category.intToCategory(category), discount);
                    case 2:  // Shoes
                        product = new ShoeItem(id, name, available, price, Gender.intToGender(gender), Category.intToCategory(category), discount);
                    default:
                        System.out.println("Unknown product type in Database: " + String.valueOf(type));
                }
                
                // add to products list
                products.add(product);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    // Print out table contents
    public void previewTable(String tableName) {
        String query = "SELECT * FROM " + tableName;

        try ( Connection con = getConnection();  Statement stmt = con.createStatement();  ResultSet rs = stmt.executeQuery(query)) {

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

    // Check if a table exists in the database
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

}
