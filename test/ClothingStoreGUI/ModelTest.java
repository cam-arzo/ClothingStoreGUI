/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ClothingStoreGUI;

import ClothingStoreGUI.Enums.Category;
import ClothingStoreGUI.Enums.DiscountType;
import ClothingStoreGUI.Enums.Gender;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class ModelTest {
    
    private static Model model;
    
    @BeforeClass
    public static void setUpClass() {
        model = new Model();
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

    /**
     * 
     * ClothingItem(int id, String name, Boolean available, BigDecimal price, Gender gender, Category category, Discount discount, DiscountType discountType)
     * 
     */
    
    // test products: no discounts, all unisex casual
    private List<Product> prepareTestProducts1() {
        List<Product> testProductList = new ArrayList<>();
        testProductList.add( new ClothingItem(1, "Product One", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(2, "Product Two", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(3, "Product Three", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(4, "Product Four", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(5, "Product Five", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        return testProductList;
    }
    
    
    
    // !! this test case is stupid
//    @Test
//    public void testUpdateProductTableInPanel() {
//        
//        // Set list of products
//        List<Product> testProducts = prepareTestProducts1();
//        model.productList = testProducts;
//        
//        // Set initial model filters
//        model.categoryFilter = Category.NONE;
//        model.genderFilter = Gender.NONE;
//        
//        // view.staffProductPanel or view.customerProductPanel
//        model.updateProductTableInPanel(panel);
//        
//        assertEquals("These are equal", 1, 1);
//        
//    }
    
}
