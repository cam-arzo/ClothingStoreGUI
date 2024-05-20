package ClothingStoreGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * VIEW:
 * - Holds GUI elements.
 * - Attaches inputs to Controller
 * 
 */

public class View extends JFrame {

    Controller controller;
    
    public View() {
        super("Clothing Store Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
    }
    
    // Create panels & switch to correct starting panel
    public void setup(Controller controller) {
        // Create panels
        UserPanel userPanel = new UserPanel(controller);  // need to pass controller so that panel buttons signal controller
        
        // Switch to starting panel
        switchPanel(userPanel);
        
        setVisible(true);
    }
    
    
    
    // Method to switch to the specified panel
    private void switchPanel(JPanel panel) {
        getContentPane().removeAll(); // Remove all panels
        getContentPane().add(panel);  // Add the specified panel
        revalidate();
        repaint();
    }
    
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    
    

    
}
