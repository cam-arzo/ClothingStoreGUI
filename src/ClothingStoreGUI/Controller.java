package ClothingStoreGUI;

/**
 * 
 * CONTROLLER:
 * 
 * Handles user input from view. This includes:
 * - Retrieving data from input boxes
 * - Validating / Cleaning input
 * 
 * Updates model depending on input. This may include:
 * - Performing calculations in model based on inputs from view
 * 
 * Navigates the application. This includes:
 * - Switching between different views/screens based on user input or application logic.
 * 
 */

public class Controller {
    
    View view;
    
    public void setView(View view) {
        this.view = view;
    }
    
    
    
    // USER PANEL methods:
    public void customerViewButtonClicked() {
        System.out.println("Test!");
    }
    
    
    
}
