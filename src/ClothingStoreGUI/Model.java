package ClothingStoreGUI;

import java.util.ArrayList;
import java.util.List;
import ClothingStoreGUI.Enums.Category;
import ClothingStoreGUI.Enums.DiscountType;
import ClothingStoreGUI.Enums.Gender;
import ClothingStoreGUI.Enums.ProductType;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * MODEL: - Does logical operations
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

        String[] stringArray = convertProductsToStringArray(productList);

        // send data as String[]
        view.staffProductPanel.updateProductTable(stringArray);
    }

    public void processAvailableProducts() {
        productList = database.getAvailableProducts();

        String[] stringArray = convertProductsToStringArray(productList);

        // send data as String[]
        view.customerProductPanel.updateProductTable(stringArray);
    }

    public void setSelectedProductFromIndex(int index) {
        if ((index >= 0) && (index < productList.size())) {
            selectedProduct = productList.get(index);
            System.out.println("SELECTED PRODUCT in model: " + selectedProduct.getName());
        }
    }

    private String[] convertProductsToStringArray(List<Product> productList) {
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

    // CUSTOMER FUNCTIONS
    // get info of the customers selected product and set components to display
    public void setCustomerSelectedProductVariables() {
        view.customerSelectionPanel.getProductNameLabel().setText(selectedProduct.cartString());
        String[] sizes = selectedProduct.getSizeSystem();
        view.customerSelectionPanel.getSizeDropdown().setModel(new javax.swing.DefaultComboBoxModel<>(sizes));
        view.customerSelectionPanel.getSizeDropdown().setSelectedIndex(2); // set size to M or 7-8 instead of smallest size by default
        view.customerSelectionPanel.getQtyPicker().setValue(1);
        // !! UNFINISHED, SELECTED PRODUCT HAS NOT BEEN ADDED TO THIS PROJECT YET
    }

    // STAFF FUNCTIONS
    // get info of the staff selected product and set components to display
    public void setNewProductVariables() {
        view.staffEditPanel.getMessage().setText("Adding new product");
        setDefaultProductSettings();
    }

// get info of the staff selected product and set components to display
    public void setStaffSelectedProductVariables() {
        view.staffEditPanel.getMessage().setText("Modifying product: "+selectedProduct.getName());
        view.staffEditPanel.getNameTextField().setText(selectedProduct.getName());
        view.staffEditPanel.getPriceTextField().setText("$"+selectedProduct.getPrice());
        view.staffEditPanel.getCategoryDropdown().setSelectedItem(selectedProduct.getCategory().getDisplayName());
        view.staffEditPanel.getGenderDropdown().setSelectedItem(selectedProduct.getGender().getDisplayName());
        view.staffEditPanel.getAvailableDropdown().setSelectedItem(selectedProduct.isAvailable());
        view.staffEditPanel.getItemTypeDropdown().setSelectedItem(selectedProduct.getProductType().getDisplayName());
        String discountType = selectedProduct.getDiscountType().getDisplayName();
        BigDecimal discountAmount = null;
        view.staffEditPanel.getDiscountDropdown().setSelectedItem(discountType);
        // gets discount amount if discount is not NONE
        if (Objects.nonNull(selectedProduct.getDiscount())) {
            discountAmount = selectedProduct.getDiscount().amount;
        }
        setDiscountStatus(discountType, discountAmount);
    }

    // changes settings in the product editing panel
    public void setDefaultProductSettings() {
        view.staffEditPanel.getNameTextField().setText("Enter new product name...");
        view.staffEditPanel.getPriceTextField().setText("$20.00");
        view.staffEditPanel.getCategoryDropdown().setModel(new javax.swing.DefaultComboBoxModel<>(Category.toStringArray()));
        view.staffEditPanel.getGenderDropdown().setModel(new javax.swing.DefaultComboBoxModel<>(Gender.toStringArray()));
        view.staffEditPanel.getAvailableDropdown().setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"True", "False"}));
        view.staffEditPanel.getItemTypeDropdown().setModel(new javax.swing.DefaultComboBoxModel<>(ProductType.toStringArray()));
        view.staffEditPanel.getDiscountDropdown().setModel(new javax.swing.DefaultComboBoxModel<>(DiscountType.toStringArray()));
        setDiscountStatus("None", null);
    }

    // sets the text field to match the discount type
    public void setDiscountStatus(String type, BigDecimal value) {
        BigDecimal amount = new BigDecimal(10);
        if (Objects.nonNull(value)) {
            amount = value;
        }
        switch (type) {
            case "None":
                view.staffEditPanel.getDiscountTextField().setText("No discount");
                view.staffEditPanel.getDiscountTextField().setEditable(false);
                break;
            case "Fixed":
                view.staffEditPanel.getDiscountTextField().setEditable(true);
                view.staffEditPanel.getDiscountTextField().setText("$"+amount.setScale(2, BigDecimal.ROUND_HALF_UP));
                break;
            case "Percent":
                view.staffEditPanel.getDiscountTextField().setEditable(true);
                view.staffEditPanel.getDiscountTextField().setText(amount.setScale(0, BigDecimal.ROUND_HALF_UP)+"%");
                break;
        }
    }
    
    // !! need to remove from database
    public void removeProduct() {
        productList.remove(selectedProduct);
        String[] stringArray = convertProductsToStringArray(productList);
        view.staffProductPanel.updateProductTable(stringArray);
        selectedProduct = null; // resetting the product list means user loses their selection
    }
    
    // returns true if saving changes is successful
    public boolean staffSaveChanges() {
        // run all checks and display error msgs if needed
        boolean validName = checkName();
        boolean validPrice = checkPrice();
        boolean validDiscount = checkDiscountAmount();

        // return false if errors found
        if (!validName || !validPrice || !validDiscount) {
            return false;
        }
        
        // name price type category gender avaliability discount amount
        String name = view.staffEditPanel.getNameTextField().getText();
        BigDecimal price = convertStringToBigDecimal(view.staffEditPanel.getPriceTextField().getText());
        ProductType productType = ProductType.fromDisplayName((String) view.staffEditPanel.getItemTypeDropdown().getSelectedItem());
        Category category = Category.fromDisplayName((String) view.staffEditPanel.getCategoryDropdown().getSelectedItem());
        Gender gender = Gender.fromDisplayName((String) view.staffEditPanel.getGenderDropdown().getSelectedItem());
        boolean available = isAvailableFromString();
        DiscountType discountType = DiscountType.fromDisplayName((String) view.staffEditPanel.getDiscountDropdown().getSelectedItem());
        BigDecimal discountAmount = convertStringToBigDecimal(view.staffEditPanel.getDiscountTextField().getText());
        Discount discount = null;
        
        switch (discountType) {
            case NONE:
                break;
            case FIXED:
                discount = new FixedDiscount(discountAmount);
                break;
            case PERCENT:
                discount = new PctDiscount(discountAmount);
                break;
        }
        
        Product product = null;
        //    public Product(int id, String name, boolean available, BigDecimal price, Gender gender, Category category, Discount discount, DiscountType discountType, ProductType productType) {
    
        // adding product NOT modifying
        // when modifying, need to get id 
        switch (productType) {
            case CLOTHING:  // Clothing
                product = new ClothingItem(-1, name, available, price, gender, category, discount, discountType);
            case SHOES:  // Shoes
                product = new ShoeItem(-1, name, available, price, gender, category, discount, discountType);
            default:
        }
        
        // !! NEED TO ADD MODIFY

        productList.add(product);
        String[] stringArray = convertProductsToStringArray(productList);
        view.staffProductPanel.updateProductTable(stringArray);
        return true;

    }
    
    public boolean isAvailableFromString() {
        String string = (String) view.staffEditPanel.getAvailableDropdown().getSelectedItem();
        if (string.equals("True")) {
            return true;
        }
        return false;
    }
    
    public boolean checkName() {
        String text = view.staffEditPanel.getNameTextField().getText();
        if (text == null || text.trim().isEmpty() || text.equals("Enter new product name...")) { // placeholder text
            System.out.println("name invalid");
            view.staffEditPanel.getNameErrorLabel().setVisible(true);
            return false;
        } else {
            view.staffEditPanel.getNameErrorLabel().setVisible(false);
            return true;
        }
    }
    
    public boolean checkPrice() {
        String text = view.staffEditPanel.getPriceTextField().getText();
        BigDecimal price = convertStringToBigDecimal(text);
        if (Objects.isNull(price)) {
            System.out.println("price invalid");
            view.staffEditPanel.getPriceErrorLabel().setVisible(true);
            return false;
        } else {
            view.staffEditPanel.getPriceErrorLabel().setVisible(false);
            return true;
        }
    }
    
    public boolean checkDiscountAmount() {
        // iff discount text field isnt editable then return true as no errors to give
        if (!view.staffEditPanel.getDiscountTextField().isEditable()) {
            return true;
        }

        String text = view.staffEditPanel.getDiscountTextField().getText();
        BigDecimal discount = convertStringToBigDecimal(text);
        if (Objects.isNull(discount)) {
            System.out.println("discount invalid");
            view.staffEditPanel.getDiscountErrorLabel().setVisible(true);
            return false;
        } else {
            view.staffEditPanel.getDiscountErrorLabel().setVisible(false);
            return true;
        }
    }
    
    public BigDecimal convertStringToBigDecimal(String string) {
        try {
            String cleanedText = string.replaceAll("[^\\d.]", ""); // remove all chars that are not decimals (d) or points (.)
            BigDecimal bigDecimal;
            if (cleanedText.matches("\\d*\\.?\\d+")) { // d* means 0 or more decimals, .? means 0 or 1 point, d+ means 1 or more decimals
                bigDecimal = new BigDecimal(cleanedText);
                return bigDecimal;
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }
}
