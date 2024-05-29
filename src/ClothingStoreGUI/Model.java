package ClothingStoreGUI;

import java.util.ArrayList;
import java.util.List;
import ClothingStoreGUI.Enums.Category;
import ClothingStoreGUI.Enums.DiscountType;
import ClothingStoreGUI.Enums.Gender;
import ClothingStoreGUI.Enums.ProductType;
import java.math.BigDecimal;
import java.util.Objects;
import javax.swing.JPanel;
import ClothingStoreGUI.Panels.PanelStaffProductView;
import ClothingStoreGUI.Panels.PanelCustomerProductView;
import javax.swing.ListSelectionModel;

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
    List<Product> filteredProductList = new ArrayList<>();
    Category categoryFilter = Category.NONE;
    Gender genderFilter = Gender.NONE;
    Product selectedProduct = null;
    OrderProduct selectedOrder = null;
    // variable to tell whether staff is adding a new product (false) or modifying an existing one (true)
    boolean isModifyingProduct = false;
    Cart cart = new Cart();

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void processAllProducts() {
        productList = database.getAllProducts();
        updateProductTableInPanel(view.staffProductPanel);
    }

    public void processAvailableProducts() {
        productList = database.getAvailableProducts();
        updateProductTableInPanel(view.customerProductPanel);
    }

    public void setSelectedProductFromIndex(int index) {
        if ((index >= 0) && (index < filteredProductList.size())) {
            selectedProduct = filteredProductList.get(index);
            System.out.println("SELECTED PRODUCT in product view: " + selectedProduct.getName());
        }
    }

    public void setCartSelectedOrderFromIndex(int index) {
        if ((index >= 0) && (index < cart.cartProducts.size())) {
            selectedOrder = cart.cartProducts.get(index);
            System.out.println("SELECTED ORDER in cart: " + selectedOrder.getProduct().getName());
        }
    }

    public void updateProductTableInPanel(JPanel panel) {
//        System.out.println("Filtering based on category "+categoryFilter+" and gender "+genderFilter); // debug
        if (!categoryFilter.equals(Category.NONE) || !genderFilter.equals(Gender.NONE)) {
            filteredProductList.clear();
            for (Product product : productList) {
                if (categoryFilter.equals(Category.NONE) || product.getCategory().equals(categoryFilter)) {
                    if (genderFilter.equals(Gender.NONE) || product.getGender().equals(genderFilter)) {
                        filteredProductList.add(product);
                    }
                }
            }
        } else {
            filteredProductList = new ArrayList<>(productList);
        }
        String[] stringArray = convertProductsToStringArray(filteredProductList);
        if (panel instanceof PanelCustomerProductView) {
            view.customerProductPanel.updateProductTable(stringArray);
        } else if (panel instanceof PanelStaffProductView) {
            view.staffProductPanel.updateProductTable(stringArray);
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
            productListLabels.add(product.toStringArray());
        }

        return productListLabels.toArray(new String[0]);
    }

    // CUSTOMER FUNCTIONS
    // get info of the customers selected product and set components to display
    public void setCustomerSelectedProductVariables() {
        view.customerSelectionPanel.getProductNameLabel().setText(selectedProduct.toStringArray());
        String[] sizes = selectedProduct.getSizeSystem();
        view.customerSelectionPanel.getSizeDropdown().setModel(new javax.swing.DefaultComboBoxModel<>(sizes));
        view.customerSelectionPanel.getSizeDropdown().setSelectedIndex(2); // set size to M or 7-8 instead of smallest size by default
        view.customerSelectionPanel.getQtyPicker().setValue(1);
    }

    public boolean customerSaveChanges() {
        boolean validQty = checkQty();

        // return false if qty is invalid
        if (!validQty) {
            return false;
        }

        String size = (String) view.customerSelectionPanel.getSizeDropdown().getSelectedItem();
        int qty = (int) view.customerSelectionPanel.getQtyPicker().getValue();

        OrderProduct newOrder = new OrderProduct(selectedProduct, size, qty);
//        System.out.println(newOrder); // debug
        cart.addItem(newOrder);
        System.out.println(cart);
        return true;
    }

    public boolean checkQty() {
        int qty = (int) view.customerSelectionPanel.getQtyPicker().getValue();
        if (qty > 0) {
            view.customerSelectionPanel.getQtyErrorLabel().setVisible(false);
            return true; // qty is positive and valid
        } else {
//            System.out.println("Quantity invalid"); // debug
            view.customerSelectionPanel.getQtyErrorLabel().setVisible(true);
            return false; // qty is negative
        }
    }

    public void displayCart() {
        view.cartPanel.getCartList().setListData(cart.toStringArray());
        selectedOrder = null;

        if (cart.getCartProducts().isEmpty()) {
            view.cartPanel.getTotalPriceLabel().setVisible(false);
            view.cartPanel.getErrorLabel().setVisible(true);
            view.cartPanel.getErrorLabel().setText("Cart is empty. Please select products to add to cart.");
            view.cartPanel.getRemoveButton().setEnabled(false);
            view.cartPanel.getModifyButton().setEnabled(false);
            view.cartPanel.getConfirmButton().setEnabled(false);
            return;
        } else {
            view.cartPanel.getTotalPriceLabel().setVisible(true);
            view.cartPanel.getTotalPriceLabel().setText("Total price: $" + cart.getTotalPrice());
            view.cartPanel.getErrorLabel().setVisible(false);
            view.cartPanel.getRemoveButton().setEnabled(true);
            view.cartPanel.getModifyButton().setEnabled(true);
            view.cartPanel.getConfirmButton().setEnabled(true);
            view.cartPanel.getErrorLabel().setText("ERROR: Please select a product");
        }

        view.cartPanel.getErrorLabel().setVisible(false);

    }

    public void removeFromCart() {
        cart.removeOrderProduct(selectedOrder);
        displayCart();
    }

    // STAFF FUNCTIONS
    // get info of the staff selected product and set components to display
    public void setNewProductVariables() {
        isModifyingProduct = false;
        view.staffEditPanel.getMessage().setText("Adding new product");
        setDefaultProductSettings();
    }

// get info of the staff selected product and set components to display
    public void setStaffSelectedProductVariables() {
        isModifyingProduct = true;
        view.staffEditPanel.getMessage().setText("Modifying product: " + selectedProduct.getName());
        view.staffEditPanel.getNameTextField().setText(selectedProduct.getName());
        view.staffEditPanel.getPriceTextField().setText("$" + selectedProduct.getPrice());
        view.staffEditPanel.getCategoryDropdown().setSelectedItem(selectedProduct.getCategory().getDisplayName());
        view.staffEditPanel.getGenderDropdown().setSelectedItem(selectedProduct.getGender().getDisplayName());
        view.staffEditPanel.getAvailableDropdown().setSelectedItem(selectedProduct.isAvailableString());
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
                view.staffEditPanel.getDiscountTextField().setText("$" + amount.setScale(2, BigDecimal.ROUND_HALF_UP));
                break;
            case "Percent":
                view.staffEditPanel.getDiscountTextField().setEditable(true);
                view.staffEditPanel.getDiscountTextField().setText(amount.setScale(0, BigDecimal.ROUND_HALF_UP) + "%");
                break;
        }
    }

    // !! need to remove from database
    public void removeProduct() {
        productList.remove(selectedProduct);
        updateProductTableInPanel(view.staffProductPanel);
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

        Product newProduct = null;
        //    public Product(int id, String name, boolean available, BigDecimal price, Gender gender, Category category, Discount discount, DiscountType discountType, ProductType productType) {

        switch (productType) {
            case CLOTHING:  // Clothing
                newProduct = new ClothingItem(-1, name, available, price, gender, category, discount, discountType);
            case SHOES:  // Shoes
                newProduct = new ShoeItem(-1, name, available, price, gender, category, discount, discountType);
            default:
        }

        if (isModifyingProduct) { // staff is modifying
            modifySelectedProduct(newProduct);
            database.modifyProductInDatabase(newProduct);
        } else { // staff is adding
            productList.add(newProduct);
            database.addProductToDatabase(newProduct);
        }
        updateProductTableInPanel(view.staffProductPanel);
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
        if (Objects.nonNull(price) && price.compareTo(BigDecimal.ZERO) > 0) {
            view.staffEditPanel.getPriceErrorLabel().setVisible(false);
            return true;
        }
        System.out.println("price invalid");
        view.staffEditPanel.getPriceErrorLabel().setVisible(true);

        return false;
    }

    public boolean checkDiscountAmount() {
        DiscountType discountType = DiscountType.fromDisplayName((String) view.staffEditPanel.getDiscountDropdown().getSelectedItem());

        // if discount text field isnt editable then return true as no errors to give
        if (discountType.equals(DiscountType.NONE)) {
            return true;
        }

        String text = view.staffEditPanel.getDiscountTextField().getText();
        BigDecimal num = convertStringToBigDecimal(text);
        if (Objects.nonNull(num)) {
            if (discountType.equals(DiscountType.FIXED) && checkPrice()) { // if fixed discount, check its above 0
                BigDecimal price = convertStringToBigDecimal(view.staffEditPanel.getPriceTextField().getText());
                if (isInRange(num, BigDecimal.ZERO, price)) {
                    view.staffEditPanel.getDiscountErrorLabel().setVisible(false);
                    return true;
                }
            } else if (discountType.equals(DiscountType.PERCENT)) { // if pct discount, check its between 0 and 100
                if (isInRange(num, BigDecimal.ZERO, BigDecimal.valueOf(100))) {
                    view.staffEditPanel.getDiscountErrorLabel().setVisible(false);
                    return true;
                }
            }
        }
        System.out.println("discount invalid");
        view.staffEditPanel.getDiscountErrorLabel().setVisible(true);
        return false;
    }

    public static boolean isInRange(BigDecimal number, BigDecimal min, BigDecimal max) {
        // Check if number is greater than min and less than or equal to max
        return number.compareTo(min) > 0 && number.compareTo(max) <= 0;
    }

    public BigDecimal convertStringToBigDecimal(String string) {
        try {
            String cleanedText = string.replaceAll("[\\s\\$\\%]", ""); // remove all whitespace and chars that are $ or %
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

    private void modifySelectedProduct(Product newProduct) {
        selectedProduct.setName(newProduct.getName());
        selectedProduct.setAvailable(newProduct.isAvailable());
        selectedProduct.setCategory(newProduct.getCategory());
        selectedProduct.setGender(newProduct.getGender());
        selectedProduct.setPrice(newProduct.getPrice());
        selectedProduct.setDiscount(newProduct.getDiscount());
        selectedProduct.setDiscountType(newProduct.getDiscountType());
        selectedProduct.setDiscountedPrice(newProduct.getDiscountedPrice());
        selectedProduct.setSizes(newProduct.getSizes());
        selectedProduct.setProductType(newProduct.getProductType());
    }

    public void setCategoryFilter(int value, JPanel panel) {
        categoryFilter = Category.intToCategory(value);
        updateProductTableInPanel(panel);
    }

    public void setGenderFilter(int value, JPanel panel) {
        genderFilter = Gender.intToGender(value);
        updateProductTableInPanel(panel);
    }

    public void reset() {
        // restart program by resetting variables and views
        selectedProduct = null;
        selectedOrder = null;
        isModifyingProduct = false;
        categoryFilter = Category.NONE;
        genderFilter = Gender.NONE;
        view.customerProductPanel.setDefaultComponentVisibilities();
        view.staffProductPanel.setDefaultComponentVisibilities();
    }

    public void printDebugProductList(List<Product> productList) {
        System.out.print("[");
        for (Product product : productList) {
            System.out.print(product.getName() + ", ");
        }
        System.out.print("]");
    }

}
