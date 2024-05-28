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
        String[] sizes = selectedProduct.getSizeSystem();
        view.customerSelectionPanel.getSizeDropdown().setModel(new javax.swing.DefaultComboBoxModel<>(sizes));
        view.customerSelectionPanel.getSizeDropdown().setSelectedIndex(2); // set size to M or 7-8 instead of smallest size by default
        view.customerSelectionPanel.getQtyPicker().setValue(1);
        // !! UNFINISHED, SELECTED PRODUCT HAS NOT BEEN ADDED TO THIS PROJECT YET
    }

    // get info of the staff selected product and set components to display
    public void setNewProductVariables() {
        view.staffEditPanel.getMessage().setText("Adding new product");
        setDefaultProductSettings();
    }

// get info of the staff selected product and set components to display
    public void setStaffSelectedProductVariables() {
        view.staffEditPanel.getMessage().setText("Modifying product: "+selectedProduct.getName());
        setDefaultProductSettings(); // delete after
        view.staffEditPanel.getNameTextField().setText(selectedProduct.getName());
        view.staffEditPanel.getPriceTextField().setText("$"+selectedProduct.getPrice());
        view.staffEditPanel.getCategoryDropdown().setSelectedItem(selectedProduct.getCategory().getDisplayName());
        view.staffEditPanel.getGenderDropdown().setSelectedItem(selectedProduct.getGender().getDisplayName());
        view.staffEditPanel.getAvailableDropdown().setSelectedItem(selectedProduct.isAvailable());
        // !! need to store item and discount type in Product
        view.staffEditPanel.getItemTypeDropdown().setSelectedItem(selectedProduct.getProductType().getDisplayName());
        String discountType = selectedProduct.getDiscountType().getDisplayName();
        BigDecimal discountAmount = null;
        view.staffEditPanel.getDiscountDropdown().setSelectedItem(discountType);
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
    void setDiscountStatus(String type, BigDecimal value) {
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
}
