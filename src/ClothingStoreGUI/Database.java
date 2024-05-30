package ClothingStoreGUI;

import ClothingStoreGUI.Enums.Category;
import ClothingStoreGUI.Enums.DiscountType;
import ClothingStoreGUI.Enums.Gender;
import ClothingStoreGUI.Enums.ProductType;
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
//            defaultData.deleteTables(stmt);
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
    
    public Product createProduct(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        boolean available = rs.getBoolean("available");
        int type = rs.getInt("type");
        String name = rs.getString("name");
        int category = rs.getInt("category");
        BigDecimal price = rs.getBigDecimal("price");
        int gender = rs.getInt("gender_id");
        int discount_int = rs.getInt("discount_id");
        BigDecimal discount_amount = rs.getBigDecimal("discount_amount");

        DiscountType discountType = DiscountType.intToDiscount(discount_int); // change int to enum form
        Discount discount = createDiscount(discountType, discount_amount);
        
        
        Product product = null;

        switch (ProductType.intToType(type)) {
            case CLOTHING:  // Clothing
//                        String name, boolean available, double price, int gender, int category, Discount discount
                product = new ClothingItem(id, name, available, price, Gender.intToGender(gender), Category.intToCategory(category), discount, discountType);
                return product;
            case SHOES:  // Shoes
                product = new ShoeItem(id, name, available, price, Gender.intToGender(gender), Category.intToCategory(category), discount, discountType);
                return product;
            default:
                System.out.println("Unknown product type in Database: " + String.valueOf(type));
                return null;
        }
    }
    
    public Order createOrder(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int quantity = rs.getInt("quantity");
        BigDecimal total_price = rs.getBigDecimal("total_price");
        
        Order order = new Order(id, quantity, total_price);
        return order;
    }
    
    public Discount createDiscount(DiscountType discountType, BigDecimal discount_amount) {
        Discount discount = null;

        switch (discountType) {
            case NONE:  // None
                break;
            case FIXED:  // Fixed
                discount = new FixedDiscount(discount_amount);
                break;
            case PERCENT:  // Percent
                discount = new PctDiscount(discount_amount);
                break;
            default:
                System.out.println("Unknown discount type in Database: " + discountType.getDisplayName());
                break;
        }
        return discount;
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

    // READ FUNCTIONS
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try ( Connection con = getConnection();  PreparedStatement pstmt = con.prepareStatement(sql);  ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Product product = createProduct(rs);

                // add to products list
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<Product> getAvailableProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE available = 1";

        try ( Connection con = getConnection();  PreparedStatement pstmt = con.prepareStatement(sql);  ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Product product = createProduct(rs);
                // add to products list
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    
    
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE available = 1";

        try ( Connection con = getConnection();  PreparedStatement pstmt = con.prepareStatement(sql);  ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Order order = createOrder(rs);
                // add to products list
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
    
    // WRITE FUNCTIONS
    public void addProductToDatabase(Product newProduct) {
        // id is -1
        // add function in category, discounttype gender and producttype enums to
        // get int from category
        //INSERT INTO products (available, type, name, category, price, gender_id, discount_id, discount_amount) VALUES (1, 0, 'Comfy Cotton T-shirt',        0, 29.99,   1, 0, null)
    
        String sql = "INSERT INTO products (available, type, name, category, price, gender_id, discount_id, discount_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            // Set the values for the placeholders
            preparedStatement.setInt(1, newProduct.isAvailable() ? 1 : 0); // available
            preparedStatement.setInt(2, newProduct.getType().toInt()); // type  // !! check this is right
            preparedStatement.setString(3, newProduct.getName()); // name
            preparedStatement.setInt(4, newProduct.getCategory().toInt()); // category
            preparedStatement.setBigDecimal(5, newProduct.getPrice()); // price
            preparedStatement.setInt(6, newProduct.getGender().toInt()); // gender_id
            preparedStatement.setInt(7, newProduct.getDiscountType().toInt()); // discount_id
            
            if (newProduct.hasDiscount())
            {
                preparedStatement.setBigDecimal(8, newProduct.getDiscount().amount);
            }
            else {
                preparedStatement.setNull(8, java.sql.Types.INTEGER);
            }
            
            // Execute the prepared statement
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Product added successfully.");
            } else {
                System.out.println("Failed to add product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }

    public void modifyProductInDatabase(Product product) {
        // use id to modify
        
        String sql =    "UPDATE products \n" +
                        "SET available = ?, \n" +
                        "    type = ?, \n" +
                        "    name = ?, \n" +
                        "    category = ?, \n" +
                        "    price = ?, \n" +
                        "    gender_id = ?, \n" +
                        "    discount_id = ?, \n" +
                        "    discount_amount = ? \n" +
                        "WHERE id = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            // Set the values for the placeholders
            preparedStatement.setInt(1, product.isAvailable() ? 1 : 0); // available
            preparedStatement.setInt(2, product.getType().toInt()); // type
            preparedStatement.setString(3, product.getName()); // name
            preparedStatement.setInt(4, product.getCategory().toInt()); // category
            preparedStatement.setBigDecimal(5, product.getPrice()); // price
            preparedStatement.setInt(6, product.getGender().toInt()); // gender_id
            preparedStatement.setInt(7, product.getDiscountType().toInt()); // discount_id
            
            if (product.hasDiscount())
            {
                preparedStatement.setBigDecimal(8, product.getDiscount().amount);
            }
            else {
                preparedStatement.setNull(8, java.sql.Types.INTEGER);
            }
            
            preparedStatement.setInt(9, product.getID());
            
            // Execute the prepared statement
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Product modified successfully.");
            } else {
                System.out.println("Failed to modify product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public void removeProductFromDatabase(Product product) {
        // use id to identify and remove
        
        String sql = "DELETE FROM products WHERE id = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            // Set the values for the placeholders
            preparedStatement.setInt(1, product.getID());
            
            // Execute the prepared statement
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Failed to delete product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public void addOrderToDatabase(int qty, BigDecimal total_price) {
        
        String sql = "INSERT INTO orders (quantity, total_price) VALUES (?, ?)";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            // Set the values for the placeholders
            preparedStatement.setInt(1, qty);
            preparedStatement.setBigDecimal(2, total_price);
            
            // Execute the prepared statement
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Order details added successfully.");
            } else {
                System.out.println("Failed to add order details.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    
    
    

}
