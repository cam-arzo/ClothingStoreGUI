package ClothingStoreGUI;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * MODEL:
 * - Does logical operations
 * 
 */

public class Model {
    
    Database database;
    
    View view;
    
    // !! might be messy storing productList and selectedProduct in Model for now
    
    List<Product> productList;
    
    Product selectedProduct = null;
    
    public void setDatabase(Database database) {
        this.database = database;
    }
    
    public void setView(View view) {
        this.view = view;
    }
    
    public void processAllProducts() {
        productList = database.getAllProducts();
        
        String[] cartString = convertProductsToCartString(productList);
        
        // send data as String[]
        view.staffProductPanel.updateProductTable(cartString);
    }
    
    public void processAvailableProducts() {
        productList = database.getAvailableProducts();
        
        String[] cartString = convertProductsToCartString(productList);
        
        // send data as String[]
        view.customerProductPanel.updateProductTable(cartString);
    }
    
    public void setSelectedProductFromIndex(int index) {
        if ((index >= 0) && (index < productList.size())) {
            selectedProduct = productList.get(index);
            System.out.println("SELECTED PRODUCT in model: " + selectedProduct.getName());
        }
    }
    
    private String[] convertProductsToCartString(List<Product> productList) {
        // debug
//        for (Product p : productList) {
//            System.out.println(p.name);
//        }
        
        // make list of products
        List<String> productListLabels = new ArrayList<>();
        
        // get cart labels for each product
        for (Product product : productList) {
            productListLabels.add(product.cartString());
        }
        
        return productListLabels.toArray(new String[0]);
    }
    
    // get info of the customers selected product and set components to display
    public void setCustomerSelectedProductVariables() {
        view.customerSelectionPanel.getProductNameLabel().setText(selectedProduct.cartString());
        String[] sizes = {"S", "M", "L"}; // place holder
        view.customerSelectionPanel.getSizeDropdown().setModel(new javax.swing.DefaultComboBoxModel<>(sizes));
        // !! UNFINISHED
    }
    
    // get info of the staff selected product and set components to display
    public void setNewProductVariables() {
    }
    
// get info of the staff selected product and set components to display
    public void setStaffSelectedProductVariables() {
    }
}