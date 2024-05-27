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

    }

    public void deleteTables(Statement stmt) throws SQLException {

        // Drop products table
        stmt.executeUpdate("DROP TABLE products");

        System.out.println("Product table dropped successfully.");

    }

    public void createProductTable(Statement stmt) throws SQLException {
        // create table
        stmt.executeUpdate("CREATE TABLE products("
                + "available SMALLINT,"
                + "id INT PRIMARY KEY,"
                + "type INT NOT NULL,"
                + "name VARCHAR(64) UNIQUE NOT NULL,"
                + "category INT NOT NULL,"
                + "price NUMERIC(6, 2) NOT NULL," // cap prices at 6 digits! and use BigDecimal
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
        stmt.executeUpdate("INSERT INTO products (available, id, type, name, category, price, gender_id, discount_id, discount_amount) VALUES "
                + "(1, 1, 0, 'Comfy Cotton T-shirt',        0, 29.99,   1, 0, null),"
                + "(0, 2, 0, 'Elegant Maxi Dress',          2, 119.99,  2, 0, null),"
                + "(1, 3, 0, 'Flannel Sleep Set',           3, 69.99,   0, 1, 30),"
                + "(1, 4, 1, 'Fluffy Sheepskin Indoor Slippers', 3, 78, 0, 0, null),"
                + "(1, 5, 0, 'Unisex Breathable Tank Top',  1, 35,      0, 2, 50),"
                + "(0, 6, 0, 'Unisex Burgundy Vest',        2, 39.99,   0, 0, null),"
                + "(1, 7, 0, 'Womens Viscose Nightie',      3, 20,      2, 0, null),"
                + "(0, 8, 0, 'Wirefree Sports Bra Black',   1, 19.99,   2, 0, null),"
                + "(1, 9, 0, 'Navy Blue Smart Tuxedo',      2, 95,      1, 0, null),"
                + "(1, 10, 1, 'Casual Slip-On Sneakers',    0, 24.99,   0, 0, null),"
                + "(1, 11, 0, 'Sophisticated Button-up Shirt', 2, 39.99,1, 2, 30),"
                + "(1, 12, 0, 'Loose Spruce Ripped Jeans',  0, 59.99,   2, 1, 25),"
                + "(1, 13, 1, 'Open-Toe Stilletos',         2, 70,      2, 0, null),"
                + "(1, 14, 0, 'Black Leather Jacket',       0, 45,      1, 2, 30)");
    }
}
