package ClothingStoreGUI;

import ClothingStoreGUI.Enums.Category;
import ClothingStoreGUI.Enums.Gender;
import java.util.Objects;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * CONTROLLER:
 *
 * Handles user input from view. This includes: - Retrieving data from input
 * boxes - Validating / Cleaning input
 *
 * Updates model depending on input. This may include: - Performing calculations
 * in model based on inputs from view
 *
 * Navigates the application. This includes: - Switching between different
 * views/screens based on user input or application logic.
 *
 */
public class Controller {

    View view;
    Model model;

    // used to go to the previous panel when pressing the back button
    JPanel previousPanel;

    public void setView(View view) {
        this.view = view;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    // set the previous panel
    public void setPreviousPanel(JPanel panel) {
        previousPanel = panel;
    }

    // get the previous panel
    public JPanel getPreviousPanel() {
        return previousPanel;
    }

    // set up action listeners for product lists so it can get selected product
    public void setUpProductListListeners() {
        // SETUP customer view functions
        // Add the list selection listener to the view's JList
        view.customerProductPanel.addProductListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    model.setSelectedProductFromIndex(view.customerProductPanel.getSelectedProductIndex());
                }
            }
        });

        // !! code repeated. might want to change
        view.staffProductPanel.addProductListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    model.setSelectedProductFromIndex(view.staffProductPanel.getSelectedProductIndex());
                }
            }
        });
        
        // When selecting order in cart, update model with the selected product
        view.cartPanel.addProductListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    model.setCartSelectedOrderFromIndex(view.cartPanel.getSelectedProductIndex());
                }
            }
        });
    }

    // USER PANEL methods:
    // View selection: 
    public void customerViewButtonClicked() {
        // show only 'Available' products
        model.processAvailableProducts();

        // go from initial screen to customer product view
        view.switchPanel(view.customerProductPanel);
    }

//    public void productSelected() {
//        String selectedItem = view.customerProductPanel.getSelectedProduct();
//        if (selectedItem != null) {
//            System.out.println("Selected: " + selectedItem);
//        }
//    }
    public void staffViewButtonClicked() {
        // SETUP staff view functions
        model.processAllProducts();
        // go from initial screen to staff product view
        view.switchPanel(view.staffProductPanel);
    }

    // GENERIC BACK BUTTON
    public void backButtonClicked() {
        // go back to the previous panel
        view.switchPanel(previousPanel);
    }

    // GENERIC RESTART BUTTON
    public void resetButtonClicked() {
        // restart program by resetting variables and views
        model.reset();
        view.switchPanel(view.userPanel);
    }

    // CUSTOMER VIEW BUTTONS
    // Customer product view:
    public void customerSelectButtonClicked() {
        // go to customer selection
        if (Objects.nonNull(model.selectedProduct)) { // check if user has selected something
            // if so, continue to selection panel
            setPreviousPanel(view.customerProductPanel);
            model.setCustomerSelectedProductVariables();
            view.customerProductPanel.getErrorLabel().setVisible(false);
            view.switchPanel(view.customerSelectionPanel);
        } else {
            // if not, show error label
            view.customerProductPanel.getErrorLabel().setVisible(true);
        }
    }

    public void cartButtonClicked() {
        setPreviousPanel(view.customerProductPanel);
        model.displayCart();
        // go to cart from customer product view
        view.switchPanel(view.cartPanel);
    }

    // Customer selection view:
    public void addToCartButtonClicked() {
        // save and return to customer product view
        if (model.customerSaveChanges()) {
            if (model.isModifyingProduct) { // if user is making changes, go back to cart to view
                view.switchPanel(view.cartPanel);
                model.isModifyingProduct = false;
            } else { // otherwise go back to product view
                view.switchPanel(view.customerProductPanel);
            }
        }
        model.updateCartLabel(); // updates the label that displays "[num] items" in cart
    }

    // Customer cart view:
    public void customerModifyButtonClicked() {
        if (Objects.nonNull(model.selectedOrder)) { // check if user has selected something
            // go to customer selection
            model.setCustomerSelectedProductVariables();
            model.setOrderModify();
            setPreviousPanel(view.cartPanel);
            view.switchPanel(view.customerSelectionPanel);
            view.cartPanel.getErrorLabel().setVisible(false);
        } else {
            // if not, show error label
            view.cartPanel.getErrorLabel().setVisible(true);
        }
    }

    public void customerRemoveButtonClicked() {
        // remove selected product from cart
        if (Objects.nonNull(model.selectedOrder)) { // check if user has selected something
            view.cartPanel.getErrorLabel().setVisible(false);
            model.removeFromCart();
        } else {
            // if not, show error label
            view.cartPanel.getErrorLabel().setVisible(true);
        }
        
    }

    public void customerConfirmButtonClicked() {
        // go to checkout from customer cart view
        model.printReceipt();
        view.switchPanel(view.checkoutPanel);
    }

    // Product view filters (both staff and customer):
    public void categoryButtonClicked(int value, JPanel panel) {
        model.setCategoryFilter(value, panel);
    }

    public void genderButtonClicked(int value, JPanel panel) {
        model.setGenderFilter(value, panel);
    }

    // STAFF VIEW BUTTONS
    // Staff product view
    public void staffAddButtonClicked() {
        view.staffProductPanel.getErrorLabel().setVisible(false); // hide error label
        setPreviousPanel(view.staffProductPanel); // set previous panel as reference for back button
        model.setNewProductVariables(); // set up the panel
        view.staffEditPanel.updatePreviousDiscountSelection();
        view.switchPanel(view.staffEditPanel);
    }

    public void staffModifyButtonClicked() {
        // move to staff modify panel from product view
        
        if (Objects.nonNull(model.selectedProduct)) { // check if user has selected something
            // if so, continue to selection panel
            setPreviousPanel(view.staffProductPanel);
            model.setStaffSelectedProductVariables();
            view.staffProductPanel.getErrorLabel().setVisible(false);
            view.staffEditPanel.updatePreviousDiscountSelection();
            view.switchPanel(view.staffEditPanel);
        } else {
            // if not, show error label
            view.staffProductPanel.getErrorLabel().setVisible(true);
        }

    }

    public void staffRemoveButtonClicked() {
        // product is removed from product list in model
        if (Objects.nonNull(model.selectedProduct)) { // check if user has selected something
            view.staffProductPanel.getErrorLabel().setVisible(false);
            model.removeProduct();
        } else {
            // if not, show error label
            view.staffProductPanel.getErrorLabel().setVisible(true);
        }
    }

    // Staff edit panel
    public void staffSaveProductButtonClicked() {
        if (model.staffSaveChanges()) {
            view.switchPanel(view.staffProductPanel);
        }
        // saves and returns to product view
        // need to read and SAVE name price category gender avaliability discount type/amount to product list
    }

    public void discountTypeModified() {
        model.setDiscountStatus((String) view.staffEditPanel.getDiscountDropdown().getSelectedItem(), null);
    }

    public void orderButtonClicked() {
        // displays order information and total revenue from orders database
        view.staffProductPanel.getErrorLabel().setVisible(false);
        setPreviousPanel(view.staffProductPanel);
        model.updateOrderInfo(); // re-display the orders table
        view.switchPanel(view.orderPanel);
    }
    
    public void nameTextFieldFieldFocused(JTextField textField) {
        // make prompt text disappear when clicked
        if (textField.getText().equalsIgnoreCase("Enter new product name..."))
        {
            textField.setText("");
        }
    }
    
    public void nameTextFieldFieldUnfocused(JTextField textField) {
        // show prompt text appear if text field is empty
        if (textField.getText().strip().equals(""))
        {
            textField.setText("Enter new product name...");
        }
    }

    
    
}
