package ClothingStoreGUI;

import ClothingStoreGUI.Enums.Category;
import ClothingStoreGUI.Enums.Gender;
import javax.swing.JPanel;

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

    // used to go to the previous panel when pressing the back button
    JPanel previousPanel;

    public void setView(View view) {
        this.view = view;
    }

    // set the previous panel
    public void setPreviousPanel(JPanel panel) {
        previousPanel = panel;
    }

    // USER PANEL methods:
    public void customerViewButtonClicked() {
        // go from initial screen to customer product view
        view.switchPanel(view.customerProductPanel);
    }

    public void staffViewButtonClicked() {
        // go from initial screen to staff product view
        view.switchPanel(view.staffProductPanel);
    }

    public void backButtonClicked() {
        // go back to the previous panel
        // !! CODE TO SET PREVIOUS PANEL HAS NOT BEEN IMPLEMENTED
        view.switchPanel(previousPanel);
    }

    public void customerSelectButtonClicked() {
        // go to customer selection
        view.switchPanel(view.customerSelectionPanel);
    }

    public void customerRemoveButtonClicked() {
        // remove selected product from cart
        // !! need a parameter for product they selected
    }

    public void customerConfirmButtonClicked() {
        // go to checkout from customer cart view
        view.switchPanel(view.checkoutPanel);
    }

    public void resetButtonClicked() {
        // restart program
        view.switchPanel(view.userPanel);
        // !! add reset button to staff product view?
    }

    public void cartButtonClicked() {
        // go to cart from customer product view
        view.switchPanel(view.cartPanel);
    }

    public void categoryButtonClicked(int value) {
        // set category filter
        // !! might need to pass in parameter for the product list display
        Category category = Category.intToCategory(value);
        // !! insert code
    }

    public void genderButtonClicked(int value) {
        // set gender filter
        // !! might need to pass in parameter for the product list display
        Gender gender = Gender.intToGender(value);
        // !! insert code
    }

    public void addToCartButtonClicked() {
        // add customised selected product to cart and returns to customer product view
        view.switchPanel(view.customerProductPanel);
    }

    public void staffSaveProductButtonClicked() {
        // saves and returns to product view
        view.switchPanel(view.staffProductPanel);
        // need to read and SAVE name price category gender avaliability discount type/amount to product list
    }

    public void staffEditButtonClicked() {
        // move to staff modify panel from product view
        view.switchPanel(view.staffEditPanel);
        // modify: need to read and LOAD name price category gender avaliability discount type/amount from the selected product
        // add: just load placeholders
    }

    public void staffRemoveButtonClicked() {
        // need to remove selected product from database
    }

}
