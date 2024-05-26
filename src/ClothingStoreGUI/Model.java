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
    
    List<Product> productList;
    
    public void setDatabase(Database database) {
        this.database = database;
    }
    
    public void setView(View view) {
        this.view = view;
    }
    
    public void processAllProducts() {
        productList = database.getAllProducts();
        // TODO update table
    }
    
    public void processAvailableProducts() {
        productList = database.getAvailableProducts();
        
        for (Product p : productList) {
            System.out.println(p.name);
        }
        
        // make list of products
        List<String> productListLabels = new ArrayList<>();
        
        // get cart labels for each product
        for (Product product : productList) {
            productListLabels.add(product.cartString());
        }
        
        for (String i : productListLabels) {
            System.out.println(i);
        }
        
        // send data as String[]
        view.customerProductPanel.updateProductTable(productListLabels.toArray(new String[0]));
    }
   
    
    
}
