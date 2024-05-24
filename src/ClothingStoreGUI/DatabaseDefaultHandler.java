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

    public void deleteTables(Statement stmt) throws SQLException {
        
        // Drop products table
        stmt.executeUpdate("DROP TABLE products");

        // Drop product_types table
        stmt.executeUpdate("DROP TABLE product_types");

        // Drop categories table
        stmt.executeUpdate("DROP TABLE categories");

        // Drop genders table
        stmt.executeUpdate("DROP TABLE genders");

        // Drop discounts table
        stmt.executeUpdate("DROP TABLE discounts");

        System.out.println("Tables dropped successfully.");
        
    }
    
    
    public void createProductTable(Statement stmt) throws SQLException {
        // create table
        stmt.executeUpdate("CREATE TABLE products("
                + "id INT PRIMARY KEY,"
                + "available SMALLINT,"
                + "type INT NOT NULL,"
                + "name VARCHAR(64) UNIQUE NOT NULL,"
                + "category INT NOT NULL,"
                + "price NUMERIC(6, 2) NOT NULL,"  // cap prices at 6 digits!
                + "gender_id INT NOT NULL,"
                + "discount_id INT,"
                + "discount_amount NUMERIC(10, 2))");
        // !! fill table?
    }

    public void createProductTypesTable(Statement stmt) throws SQLException {
        stmt.executeUpdate("CREATE TABLE product_types("
                + "id INT PRIMARY KEY,"
                + "name VARCHAR(64) UNIQUE NOT NULL)");
        // fill table
        stmt.executeUpdate("INSERT INTO product_types (id, name) VALUES "
                + "(1, 'clothing'),"
                + "(2, 'shoes')");
    }

    public void createCategoriesTable(Statement stmt) throws SQLException {
        stmt.executeUpdate("CREATE TABLE categories("
                + "id INT PRIMARY KEY,"
                + "name VARCHAR(64) UNIQUE NOT NULL)");
        // fill table
        stmt.executeUpdate("INSERT INTO categories (id, name) VALUES "
                + "(1, 'casual'),"
                + "(2, 'formal'),"
                + "(3, 'sport'),"
                + "(4, 'sleep')");
    }

    public void createGendersTable(Statement stmt) throws SQLException {
        stmt.executeUpdate("CREATE TABLE genders("
                + "id INT PRIMARY KEY,"
                + "name VARCHAR(64) UNIQUE NOT NULL)");
        // fill table
        stmt.executeUpdate("INSERT INTO genders (id, name) VALUES "
                + "(1, 'unisex'),"
                + "(2, 'male'),"
                + "(3, 'female')");
    }

    public void createDiscountsTable(Statement stmt) throws SQLException {
        stmt.executeUpdate("CREATE TABLE discounts("
                + "id INT PRIMARY KEY,"
                + "name VARCHAR(64) UNIQUE NOT NULL)");
        // fill table
        stmt.executeUpdate("INSERT INTO discounts (id, name) VALUES "
                + "(0, 'none'),"
                + "(1, 'fixed'),"
                + "(2, 'percent')");
    }

}
