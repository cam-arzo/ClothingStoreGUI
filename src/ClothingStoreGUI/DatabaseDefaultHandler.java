/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClothingStoreGUI;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for creating default tables and filling them with sample data
 */
public class DatabaseDefaultHandler {

    Database database;

    public DatabaseDefaultHandler(Database database) {
        this.database = database;
    }

    public void createTables(Statement stmt) throws SQLException {

        // holds different products
        if (!database.tableExists("products")) {
            createProductTable(stmt);
            fillProductTable(stmt);
        }
        
        // holds different order detail
        if (!database.tableExists("orders")) {
            createOrderTable(stmt);
            fillOrderTable(stmt);
        }

    }

    public void deleteTables(Statement stmt) throws SQLException {

        // Drop products table
        if (database.tableExists("products")) {
            stmt.executeUpdate("DROP TABLE products");
        }

        // Drop order table
        if (database.tableExists("orders")) {
            stmt.executeUpdate("DROP TABLE orders");
        }
        
        System.out.println("Tables dropped successfully.");

    }

    public void createProductTable(Statement stmt) throws SQLException {
        // create table
        stmt.executeUpdate("CREATE TABLE products("
                + "id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,"
                + "available SMALLINT,"
                + "type INT NOT NULL,"
                + "name VARCHAR(64) NOT NULL,"  // temporarially (?) changed product name to allow for duplicates
                + "category INT NOT NULL,"
                + "price NUMERIC(6, 2) NOT NULL,"  // cap prices at 6 digits! and use BigDecimal
                + "gender_id INT NOT NULL,"
                + "discount_id INT,"
                + "discount_amount NUMERIC(10, 2))");
    }

    public void fillProductTable(Statement stmt) throws SQLException {
        // catgories: 0-CASUAL, 1-SPORT, 2-FORMAL, 3-SLEEP, NONE;
        // gender: 0-UNISEX, 1-MALE, 2-FEMALE, NONE;
        // discount: 0-NONE, 1-FIXED, 2-PERCENT;
        // type: 0-CLOTHING, 1-SHOES;
        // (avalible[0-1], id, type[0-1], name, category[0-4], price, gender[0-3], discount[0-2], discount_amount)
        stmt.executeUpdate("INSERT INTO products (available, type, name, category, price, gender_id, discount_id, discount_amount) VALUES "
                + "(1, 0, 'Comfy Cotton T-shirt',        0, 29.99,   1, 0, null),"
                + "(1, 0, 'Elegant Maxi Dress',          2, 119.99,  2, 0, null),"
                + "(1, 0, 'Flannel Sleep Set',           3, 69.99,   0, 1, 30),"
                + "(1, 1, 'Fluffy Sheepskin Indoor Slippers', 3, 78, 0, 0, null),"
                + "(1, 0, 'Unisex Breathable Tank Top',  1, 35,      0, 2, 50),"
                + "(0, 0, 'Unisex Burgundy Vest',        2, 39.99,   0, 0, null),"
                + "(1, 0, 'Womens Viscose Nightie',      3, 20,      2, 0, null),"
                + "(0, 0, 'Wirefree Sports Bra Black',   1, 19.99,   2, 0, null),"
                + "(1, 0, 'Navy Blue Smart Tuxedo',      2, 95,      1, 0, null),"
                + "(1, 1, 'Casual Slip-On Sneakers',    0, 24.99,   0, 0, null),"
                + "(1, 0, 'Sophisticated Button-up Shirt', 2, 39.99,1, 2, 30),"
                + "(1, 0, 'Loose Spruce Ripped Jeans',  0, 59.99,   2, 1, 20),"
                + "(1, 1, 'Open-Toe Stilettos',         2, 70,      2, 0, null),"
                + "(1, 0, 'Black Leather Jacket',       0, 45,      1, 2, 30)");
    }
    
    public void createOrderTable(Statement stmt) throws SQLException {
        // create table
        stmt.executeUpdate("CREATE TABLE orders("
                + "id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,"
                + "quantity INT NOT NULL,"  // total quantity of items in the order
                + "total_price NUMERIC(10, 2))");  // total price of items in the order
    }
    
    public void fillOrderTable(Statement stmt) throws SQLException {
        stmt.executeUpdate("INSERT INTO orders (quantity, total_price) VALUES "
                + "(2, 59.98),"
                + "(3, 185.00),"
                + "(1, 119.99)");
    }
    
}
