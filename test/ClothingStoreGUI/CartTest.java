/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ClothingStoreGUI;

import ClothingStoreGUI.Enums.Category;
import ClothingStoreGUI.Enums.Gender;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;

public class CartTest {
    
    private static Cart cart;
    
    public CartTest() {
        cart = Cart.getInstance();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    // test products: no discounts, all unisex casual
    private List<Product> prepareTestProducts() {
        List<Product> testProductList = new ArrayList<>();
        testProductList.add( new ClothingItem(1, "Product One", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(2, "Product Two", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(3, "Product Three", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(4, "Product Four", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(5, "Product Five", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        return testProductList;
    }
    
    private List<OrderProduct> prepareTestOrderProducts(List<Product> testProductList) {
        List<OrderProduct> testOrderProductList = new ArrayList<>();
        // create many order products. size medium, quantity 1.
        for (Product product : testProductList) {
            testOrderProductList.add(new OrderProduct(product, "M", 1));
        }
        return testOrderProductList;
    }
    
    private List<OrderProduct> prepareTestOrderProductsMixed(List<Product> testProductList) {
        List<OrderProduct> testOrderProductList = new ArrayList<>();
        // create many order products. size medium, quantity 1 through 5.
        
        int i = 1;
        
        for (Product product : testProductList) {
            testOrderProductList.add(new OrderProduct(product, "M", i++));
        }
        return testOrderProductList;
    }
    
    private List<OrderProduct> prepareTestOrderProducts2(List<Product> testProductList) {
        List<OrderProduct> testOrderProductList = new ArrayList<>();
        // create many order products. size medium, quantity 1.
        for (Product product : testProductList) {
            testOrderProductList.add(new OrderProduct(product, "M", 1));
        }
        return testOrderProductList;
    }
    
    /**
     * Testing cart add
     * Total price and total items should increase correctly
     */
    @Test
    public void testCartAdd() {
        
        List<Product> testProducts = prepareTestProducts();
        List<OrderProduct> testOrderProducts = prepareTestOrderProducts(testProducts);
        
        cart.clear();
        
        for (OrderProduct orderProduct : testOrderProducts) {
            cart.addItem(orderProduct);
        }
        
        assertEquals("Number of items is not 5", cart.getNumItems(), 5);
        assertTrue("Total Price is not 199.95, it is " + cart.getTotalPrice().toString(), cart.getTotalPrice().compareTo(new BigDecimal("199.95")) == 0);
        
    }
    
    /**
     * Testing cart clear
     * Total price and total items should reset to 0
     */
    @Test
    public void testCartClear() {
        
        List<Product> testProducts = prepareTestProducts();
        List<OrderProduct> testOrderProducts = prepareTestOrderProducts(testProducts);
        
        cart.clear();
        
        for (OrderProduct orderProduct : testOrderProducts) {
            cart.addItem(orderProduct);
        }
        
        cart.clear();
        
        assertEquals("Number of items is not 0", cart.getNumItems(), 0);
        assertTrue("Total Price is not 0", cart.getTotalPrice().compareTo(BigDecimal.ZERO) == 0);
        
    }
    
    /**
     * Testing cart count and price after adding and removing products
     */
    @Test
    public void testCartRemove() {
        
        List<Product> testProducts = prepareTestProducts();
        List<OrderProduct> testOrderProducts = prepareTestOrderProducts(testProducts);
        
        cart.clear();
        
        for (OrderProduct orderProduct : testOrderProducts) {
            cart.addItem(orderProduct);
        }
        
        OrderProduct removeOrderProductA = testOrderProducts.get(1);
        OrderProduct removeOrderProductB = testOrderProducts.get(3);
        
        cart.removeOrderProduct(removeOrderProductA);
        cart.removeOrderProduct(removeOrderProductB);
        
        assertEquals("Number of items is not 3", cart.getNumItems(), 3);
        assertTrue("Total Price is not 119.97, it is " + cart.getTotalPrice().toString(), cart.getTotalPrice().compareTo(new BigDecimal("119.97")) == 0);
    }
    
    /**
     * Testing count and price operations on more complicated data
     * - Adds 5 unique products with unique amounts
     * - Adds 2 additional duplicate product orders which should be merged
     * - Removes some products
     */
    @Test
    public void testCartComplex() {
        
        List<Product> testProducts = prepareTestProducts();
        List<OrderProduct> testOrderProducts = prepareTestOrderProductsMixed(testProducts);
        
        // add a copy of the 1st product
        testOrderProducts.add(testOrderProducts.get(0));
        
        // add a copy of the 2nd product
        testOrderProducts.add(testOrderProducts.get(1));
        
        // 18 products total
        
        cart.clear();
        
        for (OrderProduct orderProduct : testOrderProducts) {
            cart.addItem(orderProduct);
        }
        
        // remove 1st product (should reduce by 2, since these merged)
        OrderProduct removeOrderProductA = testOrderProducts.get(0);
        
        cart.removeOrderProduct(removeOrderProductA);
        
        // should be 16 products total
        
        assertEquals("Number of items is not 16, it is " + String.valueOf(cart.getNumItems()), cart.getNumItems(), 16);
        assertTrue("Total Price is not 639.84, it is " + cart.getTotalPrice().toString(), cart.getTotalPrice().compareTo(new BigDecimal("639.84")) == 0);
        
    }
    
}
