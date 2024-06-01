package ClothingStoreGUI;

import ClothingStoreGUI.Enums.Category;
import ClothingStoreGUI.Enums.DiscountType;
import ClothingStoreGUI.Enums.Gender;
import ClothingStoreGUI.Panels.PanelStaffModify;
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
    private List<Product> prepareTestProducts() {
        List<Product> testProductList = new ArrayList<>();
        testProductList.add( new ClothingItem(1, "Product One", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(2, "Product Two", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(3, "Product Three", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(4, "Product Four", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        testProductList.add( new ClothingItem(5, "Product Five", true, new BigDecimal(39.99), Gender.UNISEX, Category.CASUAL, null, null) );
        return testProductList;
    }
    
    @Test
    public void testCheckName() {
        
        // Setup controller, view
        Controller controller = new Controller();
        View view = new View();
        Model model = new Model();
        model.view = view;
        // Setup panel
        view.staffEditPanel = new PanelStaffModify(controller);
        
        // TESTING VALID TEXT ENTRIES
        view.staffEditPanel.getNameTextField().setText("Test entry");
        assertTrue("Text should be valid: " + view.staffEditPanel.getNameTextField().getText(), model.checkName());
        view.staffEditPanel.getNameTextField().setText("jeans");
        assertTrue("Text should be valid: " + view.staffEditPanel.getNameTextField().getText(), model.checkName());
        
        // TESTING INVALID TEXT ENTRIES
        view.staffEditPanel.getNameTextField().setText("      ");
        assertFalse("Text should be invalid: " + view.staffEditPanel.getNameTextField().getText(), model.checkName());
        view.staffEditPanel.getNameTextField().setText("");
        assertFalse("Text should be invalid: " + view.staffEditPanel.getNameTextField().getText(), model.checkName());
        view.staffEditPanel.getNameTextField().setText("   \n\n   ");
        assertFalse("Text should be invalid: " + view.staffEditPanel.getNameTextField().getText(), model.checkName());
        view.staffEditPanel.getNameTextField().setText("Enter new product name...");
        assertFalse("Text should be invalid: " + view.staffEditPanel.getNameTextField().getText(), model.checkName());
        
    }
    
    @Test
    public void testCheckPrice() {
        
        // Setup controller, view
        Controller controller = new Controller();
        View view = new View();
        Model model = new Model();
        model.view = view;
        // Setup panel
        view.staffEditPanel = new PanelStaffModify(controller);
        
        // TESTING VALID TEXT ENTRIES
        view.staffEditPanel.getPriceTextField().setText("$25");
        assertTrue("Text should be valid: " + view.staffEditPanel.getPriceTextField().getText(), model.checkPrice());
        view.staffEditPanel.getPriceTextField().setText("$25.00");
        assertTrue("Text should be valid: " + view.staffEditPanel.getPriceTextField().getText(), model.checkPrice());
        view.staffEditPanel.getPriceTextField().setText("0.50");
        assertTrue("Text should be valid: " + view.staffEditPanel.getPriceTextField().getText(), model.checkPrice());
        view.staffEditPanel.getPriceTextField().setText("0.93");
        assertTrue("Text should be valid: " + view.staffEditPanel.getPriceTextField().getText(), model.checkPrice());
        view.staffEditPanel.getPriceTextField().setText("39.90");
        assertTrue("Text should be valid: " + view.staffEditPanel.getPriceTextField().getText(), model.checkPrice());
        
        // TESTING INVALID TEXT ENTRIES
        view.staffEditPanel.getPriceTextField().setText("Test entry");
        assertFalse("Text should be invalid: " + view.staffEditPanel.getPriceTextField().getText(), model.checkPrice());
        view.staffEditPanel.getPriceTextField().setText("       ");
        assertFalse("Text should be invalid: " + view.staffEditPanel.getPriceTextField().getText(), model.checkPrice());
        view.staffEditPanel.getPriceTextField().setText("123.42.12");
        assertFalse("Text should be invalid: " + view.staffEditPanel.getPriceTextField().getText(), model.checkPrice());
        view.staffEditPanel.getPriceTextField().setText("123..00");
        assertFalse("Text should be invalid: " + view.staffEditPanel.getPriceTextField().getText(), model.checkPrice());
        view.staffEditPanel.getPriceTextField().setText("123.");
        assertFalse("Text should be invalid: " + view.staffEditPanel.getPriceTextField().getText(), model.checkPrice());
        view.staffEditPanel.getPriceTextField().setText("12a");
        assertFalse("Text should be invalid: " + view.staffEditPanel.getPriceTextField().getText(), model.checkPrice());
        
    }
    
    @Test
    public void testCheckDiscountAmount() {
        
        // Setup controller, view
        Controller controller = new Controller();
        View view = new View();
        Model model = new Model();
        model.view = view;
        // Setup panel
        view.staffEditPanel = new PanelStaffModify(controller);
        
        // Fixed or Percent
//        view.staffEditPanel.getDiscountDropdown().getSelectedItem()
        
        // TESTING VALID TEXT ENTRIES
        view.staffEditPanel.getNameTextField().setText("Test entry");
        assertTrue("Text should be valid: " + view.staffEditPanel.getNameTextField().getText(), model.checkName());
        
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
