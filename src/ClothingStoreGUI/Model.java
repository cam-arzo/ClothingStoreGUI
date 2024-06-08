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
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * MODEL: - Does logical operations
 *
 */
public class Model {

    Database database;
    View view;
    List<Product> productList;
    List<Product> filteredProductList = new ArrayList<>();
    Category categoryFilter = Category.NONE;
    Gender genderFilter = Gender.NONE;
    Product selectedProduct = null;
    OrderProduct selectedOrder = null;
    // variable to tell whether user is adding (false) or modifying (true) a product or order
    boolean isModifyingProduct = false;
    BigDecimal total_revenue = BigDecimal.ZERO;
    List<Order> orderList = new ArrayList<>();
    Cart cart = Cart.getInstance();

    public void setDatabase(Database database) {
        // set database instance
        this.database = database;
    }

    public void setView(View view) {
        // set view instance
        this.view = view;
    }

    public void processAllProducts() {
        // retrieve all products from database (for staff view)
        productList = database.getAllProducts();
        updateProductTableInPanel(view.staffProductPanel);
    }

    public void processAvailableProducts() {
        // retrieve only available products from database (for customer view)
        productList = database.getAvailableProducts();
        updateProductTableInPanel(view.customerProductPanel);
    }

    public void setSelectedProductFromIndex(int index) {
        // get the product that the user has highlighted in the product list
        if ((index >= 0) && (index < filteredProductList.size())) {
            selectedProduct = filteredProductList.get(index);
            System.out.println("SELECTED PRODUCT in product view: " + selectedProduct.getName());
        }
    }

    // update selected order in model, !! update cart with selected index
    public void setCartSelectedOrderFromIndex(int index) {
        // get the order that the user has selected in cart view
        if ((index >= 0) && (index < cart.getCartProducts().size())) {
            selectedOrder = cart.getCartProducts().get(index);
            System.out.println("SELECTED ORDER in cart: " + selectedOrder.getProduct().getName());
        }
    }

    public void updateProductTableInPanel(JPanel panel) {
        // display products based on the chosen category and gender
//        System.out.println("Filtering based on category "+categoryFilter+" and gender "+genderFilter); // debug
        if (!categoryFilter.equals(Category.NONE) || !genderFilter.equals(Gender.NONE)) {
            filteredProductList.clear(); // make sure sure product list is empty
            for (Product product : productList) {
                if (categoryFilter.equals(Category.NONE) || product.getCategory().equals(categoryFilter)) {
                    if (genderFilter.equals(Gender.NONE) || product.getGender().equals(genderFilter)) {
                        filteredProductList.add(product); // add product to list if it fits the current category and gender
                    }
                }
            }
        } else {
            filteredProductList = new ArrayList<>(productList); // if there are no filters, show all products
        }
        String[] stringArray = convertProductsToStringArray(filteredProductList);
        // find what list to update (staff or customer) by checking the panel
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
            productListLabels.add(product.toString());
        }

        return productListLabels.toArray(new String[0]);
    }

    // CUSTOMER FUNCTIONS
    // get info of the customers selected product and set components to display
    // !! need to use info from Selected Order if modifying cart item
    public void setCustomerSelectedProductVariables() {
        isModifyingProduct = false; // adding a new order product
        view.customerSelectionPanel.getProductNameLabel().setText(selectedProduct.toString());
        String[] sizes = selectedProduct.getSizeSystem();
        view.customerSelectionPanel.getSizeDropdown().setModel(new javax.swing.DefaultComboBoxModel<>(sizes));
        view.customerSelectionPanel.getSizeDropdown().setSelectedIndex(2); // set size to M or 7-8 instead of smallest size by default
        view.customerSelectionPanel.getQtyPicker().setValue(1);
        view.customerSelectionPanel.getAddToCartButton().setText("Add to cart");
    }

    // get info on OrderProduct when user selects it to modify
    public void setOrderModify() {
        isModifyingProduct = true; // modifying an order
        view.customerSelectionPanel.getProductNameLabel().setText(selectedOrder.getProduct().toString());
        String[] sizes = selectedOrder.getProduct().getSizeSystem();
        view.customerSelectionPanel.getSizeDropdown().setModel(new javax.swing.DefaultComboBoxModel<>(sizes));
        view.customerSelectionPanel.getSizeDropdown().setSelectedItem(selectedOrder.getSize());
        view.customerSelectionPanel.getQtyPicker().setValue(selectedOrder.getQuantity());
        view.customerSelectionPanel.getAddToCartButton().setText("Save changes");
    }

    // returns true if changes are saved successfully
    public boolean customerSaveChanges() {
        // validate inputs (quantity must be positive)
        boolean validQuantity = checkQuantity();

        // return false if qty is invalid
        if (!validQuantity) {
            return false;
        }

        // get variables
        String size = (String) view.customerSelectionPanel.getSizeDropdown().getSelectedItem();
        int qty = (int) view.customerSelectionPanel.getQtyPicker().getValue();
        
        if (isModifyingProduct) { // user is modifying
            // make new OrderProduct from the selected order in CART
            // (don't use SelectedProduct as this is the product from the product selection screen!)
            OrderProduct newOrder = new OrderProduct(selectedOrder.getProduct(), size, qty);
            cart.updateCart(selectedOrder, newOrder);
        } else { // user is adding
            // make new OrderProduct from the selected product in PRODUCT VIEW
            OrderProduct newOrder = new OrderProduct(selectedProduct, size, qty);
            cart.addItem(newOrder);
        }
        displayCart();

        return true;
    }

    // check quantity is positive
    public boolean checkQuantity() {
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

    // update cart display
    public void displayCart() {
        view.cartPanel.getCartList().setListData(cart.toStringArray());
        selectedOrder = null; // reset selected order as it becomes automatically deselected
        updateCartLabel();

        if (cart.getCartProducts().isEmpty()) {
            // disable all interactions if there are no products in cart
            view.cartPanel.getTotalPriceLabel().setVisible(false);
            view.cartPanel.getErrorLabel().setVisible(true);
            view.cartPanel.getErrorLabel().setText("Cart is empty. Please select products to add to cart.");
            view.cartPanel.getRemoveButton().setEnabled(false);
            view.cartPanel.getModifyButton().setEnabled(false);
            view.cartPanel.getConfirmButton().setEnabled(false);
            return;
        } else {
            // enable interactions
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

    // remove item from cart
    public void removeFromCart() {
        cart.removeOrderProduct(selectedOrder);
        displayCart();
    }

    // update label that shows '0 items' next to cart button in customer product panel
    public void updateCartLabel() {
        int numItems = cart.getNumItems();
        String labelText = numItems + " item" + (numItems != 1 ? "s" : ""); // show "item" if only 1 item, otherwise show "items"
        view.customerProductPanel.getNumItemsLabel().setText(labelText);
    }
    
    // show receipt in table in checkout panel
    public void printReceipt() {
        JTable table = view.checkoutPanel.getReceiptTable();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Name", "Size", "Qty", "Price"}, 0); // 0 rows at first

        // add orders to table
        for (OrderProduct order : cart.getCartProducts()) {
            Object[] row = {order.getProduct().getName(), order.getSize(), order.getQuantity(), "$" + order.getTotalPrice().toString()};
            model.addRow(row);
        }
        table.setModel(model);

        // set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(300);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);

        // show total price
        view.checkoutPanel.getTotalPriceLabel().setText("Total price: $" + cart.getTotalPrice());
        // update orders database
        database.addOrderToDatabase(cart.getNumItems(), cart.getTotalPrice());
    }

    // STAFF FUNCTIONS
    // get info of the staff selected product and set components to display
    public void setNewProductVariables() {
        isModifyingProduct = false; // staff is adding a new product
        view.staffEditPanel.getMessage().setText("Adding new product");
        setDefaultProductSettings(); // set components to show correct options e.g. gender dropdown
    }

// get info of the staff selected product and set components to display
    public void setStaffSelectedProductVariables() {
        setDefaultProductSettings(); // set components to show correct options e.g. gender dropdown
        isModifyingProduct = true; // user is modifying an existing product
        view.staffEditPanel.getMessage().setText("Modifying product: " + selectedProduct.getName());
        
        // get and set the existing product information
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
        BigDecimal amount = new BigDecimal(10); // set amount to 10 as default in case value hasnt been passed in
        if (Objects.nonNull(value)) {
            amount = value;
        }
        switch (type) {
            case "None": // displays "No discount"
                view.staffEditPanel.getDiscountTextField().setText("No discount");
                view.staffEditPanel.getDiscountTextField().setEditable(false);
                break;
            case "Fixed": // displays "$10"
                view.staffEditPanel.getDiscountTextField().setEditable(true);
                view.staffEditPanel.getDiscountTextField().setText("$" + amount.setScale(2, BigDecimal.ROUND_HALF_UP));
                break;
            case "Percent": // displays "10%"
                view.staffEditPanel.getDiscountTextField().setEditable(true);
                view.staffEditPanel.getDiscountTextField().setText(amount.setScale(0, BigDecimal.ROUND_HALF_UP) + "%");
                break;
        }
    }

    // remove product from database
    public void removeProduct() {
        productList.remove(selectedProduct);
        database.removeProductFromDatabase(selectedProduct);
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

        // gets name price type category gender avaliability discount amount
        String name = view.staffEditPanel.getNameTextField().getText();
        BigDecimal price = convertStringToBigDecimal(view.staffEditPanel.getPriceTextField().getText());
        ProductType productType = ProductType.fromDisplayName((String) view.staffEditPanel.getItemTypeDropdown().getSelectedItem());
        Category category = Category.fromDisplayName((String) view.staffEditPanel.getCategoryDropdown().getSelectedItem());
        Gender gender = Gender.fromDisplayName((String) view.staffEditPanel.getGenderDropdown().getSelectedItem());
        boolean available = isAvailableFromString();
        DiscountType discountType = DiscountType.fromDisplayName((String) view.staffEditPanel.getDiscountDropdown().getSelectedItem());
        BigDecimal discountAmount = convertStringToBigDecimal(view.staffEditPanel.getDiscountTextField().getText());
        Discount discount = null;

        // create discount based on type
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
        
        // Stores product information from Edit Panel GUI
        Product newProduct = null;
        //    public Product(int id, String name, boolean available, BigDecimal price, Gender gender, Category category, Discount discount, DiscountType discountType, ProductType productType) {

        // create product based on product type
        switch (productType) {
            case CLOTHING:  // Clothing
                newProduct = new ClothingItem(-1, name, available, price, gender, category, discount, discountType);
                break;
            case SHOES:  // Shoes
                newProduct = new ShoeItem(-1, name, available, price, gender, category, discount, discountType);
                break;
            default:
        }

        if (isModifyingProduct) { // staff is modifying
            // change existing product in product list and in database
            modifySelectedProduct(newProduct);
            database.modifyProductInDatabase(selectedProduct);
        } else { // staff is adding
            // add product to product list and to database
            productList.add(newProduct);
            database.addProductToDatabase(newProduct);
        }
        updateProductTableInPanel(view.staffProductPanel);
        return true;

    }

    // returns true or false by reading the availability dropdown
    public boolean isAvailableFromString() {
        String string = (String) view.staffEditPanel.getAvailableDropdown().getSelectedItem();
        if (string.equals("True")) {
            return true;
        }
        return false;
    }

    // checks name text field to make sure it is not empty, shows or hides error message
    public boolean checkName() {
        String text = view.staffEditPanel.getNameTextField().getText();
        if (text == null || text.strip().isEmpty() || text.equals("Enter new product name...")) { // placeholder text
            System.out.println("name invalid");
            view.staffEditPanel.getNameErrorLabel().setVisible(true);
            return false;
        } else {
            view.staffEditPanel.getNameErrorLabel().setVisible(false);
            return true;
        }
    }

    // checks price to make sure it is not null or negative, shows or hides error message
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

    // checks discount next field for valid amount, shows or hides error message
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

    // reads string input from price text fields and converts to big decimal
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
    
    // Update selected product with new information from temporary product (product info retrieved from GUI)
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
        selectedProduct.setType(newProduct.getProductType());
    }

    // set user-selected category filter
    public void setCategoryFilter(int value, JPanel panel) {
        categoryFilter = Category.intToCategory(value);
        updateProductTableInPanel(panel);
    }

    // set user-selected gender filter
    public void setGenderFilter(int value, JPanel panel) {
        genderFilter = Gender.intToGender(value);
        updateProductTableInPanel(panel);
    }

    // resets and goes back to view selection panel
    public void reset() {
        // restart program by resetting variables and views
        cart.reset();
        updateCartLabel();
        selectedProduct = null;
        selectedOrder = null;
        isModifyingProduct = false;
        categoryFilter = Category.NONE;
        genderFilter = Gender.NONE;
        view.customerProductPanel.setDefaultComponentVisibilities();
        view.staffProductPanel.setDefaultComponentVisibilities();
    }

    // debug function
    public void printDebugProductList(List<Product> productList) {
        System.out.print("[");
        for (Product product : productList) {
            System.out.print(product.getName() + ", ");
        }
        System.out.print("]");
    }

    // displays order table in staff view
    public void updateOrderInfo() {
        // shows orders and total revenue
        orderList = database.getOrders();
        total_revenue = database.getTotalRevenue();
        
        JTable table = view.orderPanel.getOrderTable();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Order number",  "Total items bought", "Total price paid"}, 0); // 0 rows at first
        int order_no = 1;
        
        // add orders to table
        for (Order order : orderList) {
            Object[] row = {order_no++, order.getQuantity(), "$" + order.getTotalPrice()};
            model.addRow(row);
        }
        table.setModel(model);

        view.orderPanel.getTotalRevenueLabel().setText("Total revenue: $"+total_revenue);
    }

}
