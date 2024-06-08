package ClothingStoreGUI;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Admin
 */
public class DatabaseTest {
    
    private static Database database;
    
    @BeforeClass
    public static void setUpClass() {
        database = new Database();
        database.setup();
    }

    /**
     * Test of getConnection method, of class Database.
     */
    @Test
    public void testGetConnection() throws Exception {
        try (Connection connection = database.getConnection()) {
            assertNotNull("Connection should not be null", connection);
            System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            fail("SQL Exception: " + e.getMessage());
        }
    }

    /**
     * Test of tableExists()
     * Creates and Removes a test table
     */
    @Test
    public void testTableExists() {
        try (Connection connection = database.getConnection();
            Statement stmt = connection.createStatement()) {
            
            try {
                stmt.executeUpdate("DROP TABLE test");
                System.out.println("Removing test table before test.");
            } catch(SQLException e) {
                System.out.println("No test table present:");
                System.out.println(e.getMessage());
            }
            
            System.out.println("Running Test.");
            
            assertFalse("Test table shouldn't exist", database.tableExists("test"));
            
            stmt.executeUpdate("CREATE TABLE test("
                + "id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY)");
            
            assertTrue("Test table should exist", database.tableExists("test"));
            assertFalse("Test table 2 shouldn't exist", database.tableExists("test2"));
            
            stmt.executeUpdate("DROP TABLE test");
            
            assertFalse("Test table shouldn't exist", database.tableExists("test"));
            
        } catch (SQLException e) {
            fail("SQL Exception: " + e.getMessage());
        }
    }
    
    /**
     * Test of setup method, of class Database.
     */
    @Test
    public void testSetup() {
        try (Connection connection = database.getConnection();
            Statement stmt = connection.createStatement()) {
            
            // Check if the products table exists
            assertTrue("Products table should exist", database.tableExists("products"));
            // Check if the orders table exists
            assertTrue("Orders table should exist", database.tableExists("orders"));

            // Verify products table has data
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM products");
            rs.next();
            int count = rs.getInt("rowcount");
            assertTrue("Products table should have data", count > 0);

            // Verify orders table has data
            rs = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM orders");
            rs.next();
            count = rs.getInt("rowcount");
            assertTrue("Orders table should have data", count > 0);

        } catch (SQLException e) {
            fail("SQL Exception: " + e.getMessage());
        }
    }
}
