package ClothingStoreGUI;

import ClothingStoreGUI.Enums.Category;
import ClothingStoreGUI.Enums.DiscountType;
import ClothingStoreGUI.Enums.Gender;
import ClothingStoreGUI.Panels.PanelCustomerSelection;
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
        controller.model = model;
        controller.view = view;
        // Setup panel
        view.staffEditPanel = new PanelStaffModify(controller);
        
        // Prepare dropboxes
        model.setNewProductVariables();
        // Update 'previous selection' so that it isnt null
        view.staffEditPanel.updatePreviousDiscountSelection();
        
        // Set to Fixed
        view.staffEditPanel.getDiscountDropdown().setSelectedIndex(1);
        
        // TESTING VALID TEXT ENTRIES for Fixed Discount
        view.staffEditPanel.getDiscountTextField().setText("$10.00");
        assertTrue("Text should be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("$15.50");
        assertTrue("Text should be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("0.31");
        assertTrue("Text should be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("20.00");
        assertTrue("Text should be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("        5   ");
        assertTrue("Text should be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("0.1");
        assertTrue("Text should be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        
        // TESTING INVALID TEXT ENTRIES for Fixed Discount
        // default price is 20, so $40.00 should be too much
        view.staffEditPanel.getDiscountTextField().setText("test");
        assertFalse("Text shouldn't be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("$40.00");
        assertFalse("Text shouldn't be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("20.01");
        assertFalse("Text shouldn't be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("    \n   ");
        assertFalse("Text shouldn't be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("-10");
        assertFalse("Text shouldn't be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("0.00");
        assertFalse("Text shouldn't be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        
        // Set to Percent
        view.staffEditPanel.getDiscountDropdown().setSelectedIndex(2);
        
        view.staffEditPanel.getDiscountTextField().setText("50.00");
        assertTrue("Text should be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("100%");
        assertTrue("Text should be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("   10 ");
        assertTrue("Text should be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("   0.2 ");
        assertTrue("Text should be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        
        // TESTING INVALID TEXT ENTRIES for Percent Discount
        view.staffEditPanel.getDiscountTextField().setText("test");
        assertFalse("Text shouldn't be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        // 150% is too much
        view.staffEditPanel.getDiscountTextField().setText("150.00");
        assertFalse("Text shouldn't be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("100.01");
        assertFalse("Text shouldn't be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("-10");
        assertFalse("Text shouldn't be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("   \n ");
        assertFalse("Text shouldn't be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        view.staffEditPanel.getDiscountTextField().setText("0.0");
        assertFalse("Text shouldn't be valid: " + view.staffEditPanel.getDiscountTextField().getText(), model.checkDiscountAmount());
        
    }
    
    
    
    @Test
    public void testCheckQuantity() {
        
        // Setup controller, view
        Controller controller = new Controller();
        View view = new View();
        Model model = new Model();
        model.view = view;
        // Setup panel
        view.customerSelectionPanel = new PanelCustomerSelection(controller);
        
        // TESTING VALID TEXT ENTRIES
        view.customerSelectionPanel.getQtyPicker().setValue(12);
        assertTrue("Text should be valid: " + view.customerSelectionPanel.getQtyPicker().getValue(), model.checkQuantity());
        
        // TESTING INVALID TEXT ENTRIES
        view.customerSelectionPanel.getQtyPicker().setValue(-10);
        assertFalse("Text should be valid: " + view.customerSelectionPanel.getQtyPicker().getValue(), model.checkQuantity());
        view.customerSelectionPanel.getQtyPicker().setValue(0);
        assertFalse("Text should be valid: " + view.customerSelectionPanel.getQtyPicker().getValue(), model.checkQuantity());
        
    }
    
}
