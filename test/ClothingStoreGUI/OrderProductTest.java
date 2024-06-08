package ClothingStoreGUI;

import ClothingStoreGUI.Enums.Category;
import ClothingStoreGUI.Enums.DiscountType;
import ClothingStoreGUI.Enums.Gender;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Test toString
 * 
 * Test getting total price
 * 
 */

public class OrderProductTest {
    
    // make test products: no discounts, all unisex casual
    private List<Product> prepareTestProducts() {
        List<Product> testProductList = new ArrayList<>();
        testProductList.add( new ClothingItem(1, "Product One", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(2, "Product Two", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(3, "Product Three", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(4, "Product Four", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(5, "Product Five", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        return testProductList;
    }
    
    // make mixed test products
    private List<Product> prepareMixedTestProducts() {
        List<Product> testProductList = new ArrayList<>();
        testProductList.add( new ClothingItem(6, "cheap thing", true, new BigDecimal(0.50), Gender.MALE, Category.CASUAL, null, null) );
        testProductList.add( new ShoeItem(7, "Sport Shoes", true, new BigDecimal(69.99), Gender.UNISEX, Category.SPORT, new PctDiscount(new BigDecimal(50)), DiscountType.PERCENT) );
        testProductList.add( new ClothingItem(8, "Unavailable gold Pajamas", false, new BigDecimal(799.99), Gender.FEMALE, Category.SLEEP, new FixedDiscount(new BigDecimal(40)), DiscountType.FIXED) );
        testProductList.add( new ClothingItem(9, "Ordinary Business Pants", true, new BigDecimal(74.50), Gender.UNISEX, Category.FORMAL, null, DiscountType.NONE) );
        return testProductList;
    }
    
    // test order products
    private List<OrderProduct> prepareTestOrderProducts(List<Product> testProductList) {
        List<OrderProduct> testOrderProductList = new ArrayList<>();
        // create many order products. size medium, quantity 1.
        for (Product product : testProductList) {
            testOrderProductList.add(new OrderProduct(product, "M", 1));
        }
        return testOrderProductList;
    }
    
    public OrderProductTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    // Test total prices for a range of products
    // Total price includes adjusting the price for any discounts and for quantity
    
    @Test
    public void testTotalPrice() {
        // generate a mixed range of test instances for 'OrderProduct'
        List<OrderProduct> testOrderProductList = prepareTestOrderProducts(prepareMixedTestProducts());
        
        // test basic price check 
        assertTrue(testOrderProductList.get(0).getTotalPrice().compareTo(new BigDecimal("0.50")) == 0);
        // test discounts
        assertTrue(testOrderProductList.get(1).getTotalPrice().compareTo(new BigDecimal("34.99")) == 0);
        assertTrue(testOrderProductList.get(2).getTotalPrice().compareTo(new BigDecimal("759.99")) == 0);
        
        // test regular product order but update quantity
        assertTrue(testOrderProductList.get(3).getTotalPrice().compareTo(new BigDecimal("74.50")) == 0);
        testOrderProductList.get(3).setQuantity(16);
        assertTrue(testOrderProductList.get(3).getTotalPrice().compareTo(new BigDecimal("1192.00")) == 0);
        
    }
        
    // Test toString for a range of products
    
    @Test
    public void testToStringSimple() {
        // generate a mixed range of test instances for 'OrderProduct'
        List<OrderProduct> testOrderProductList = prepareTestOrderProducts(prepareTestProducts());
        
        assertEquals("Product One, Size M [x1] - $39.99", testOrderProductList.get(0).toString());
        assertEquals("Product Two, Size M [x1] - $39.99", testOrderProductList.get(1).toString());
        assertEquals("Product Three, Size M [x1] - $39.99", testOrderProductList.get(2).toString());
        assertEquals("Product Four, Size M [x1] - $39.99", testOrderProductList.get(3).toString());
        assertEquals("Product Five, Size M [x1] - $39.99", testOrderProductList.get(4).toString());
    }
    
    @Test
    public void testToStringMixed() {
        // generate a mixed range of test instances for 'OrderProduct'
        List<OrderProduct> testOrderProductList = prepareTestOrderProducts(prepareMixedTestProducts());
        
        assertEquals("cheap thing, Size M [x1] - $0.50", testOrderProductList.get(0).toString());
        assertEquals("Sport Shoes, Size M [x1] - $34.99 (50% off!)", testOrderProductList.get(1).toString());
        assertEquals("Unavailable gold Pajamas, Size M [x1] - $759.99 ($40 off!)", testOrderProductList.get(2).toString());
        assertEquals("Ordinary Business Pants, Size M [x1] - $74.50", testOrderProductList.get(3).toString());
        
        // changing size and quantity
        testOrderProductList.get(3).setQuantity(16);
        testOrderProductList.get(3).setSize("XXL");
        assertEquals("Ordinary Business Pants, Size XXL [x16] - $1192.00", testOrderProductList.get(3).toString());
    }
    
}
