package ClothingStoreGUI;

import ClothingStoreGUI.Panels.PanelCheckout;
import ClothingStoreGUI.Panels.PanelCustomerSelection;
import ClothingStoreGUI.Panels.PanelViewSelection;
import ClothingStoreGUI.Panels.PanelStaffProductView;
import ClothingStoreGUI.Panels.PanelCustomerProductView;
import ClothingStoreGUI.Panels.PanelCart;
import ClothingStoreGUI.Panels.PanelOrderInfo;
import ClothingStoreGUI.Panels.PanelStaffModify;
import java.util.Objects;
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
        
    PanelViewSelection userPanel;
    PanelCustomerProductView customerProductPanel;
    PanelCustomerSelection customerSelectionPanel;
    PanelCart cartPanel;
    PanelCheckout checkoutPanel;
    PanelStaffProductView staffProductPanel;
    PanelStaffModify staffEditPanel;
    PanelOrderInfo orderPanel;
    
    public View() {
        super("Clothing Store Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
    }
    
        public void setController(Controller controller) {
        this.controller = controller;
    }
    
    // Create panels & switch to correct starting panel
    public void setup(Controller controller) {
        
        // Create panels
        userPanel = new PanelViewSelection(controller);  // need to pass controller so that panel buttons signal controller
        customerProductPanel = new PanelCustomerProductView(controller);
        customerSelectionPanel = new PanelCustomerSelection(controller);
        cartPanel = new PanelCart(controller);
        checkoutPanel = new PanelCheckout(controller);
        staffProductPanel = new PanelStaffProductView(controller);
        staffEditPanel = new PanelStaffModify(controller);
        orderPanel = new PanelOrderInfo(controller);
        controller.setUpProductListListeners();
        
        // Switch to starting panel
        switchPanel(userPanel);
        
        setVisible(true);
    }
    
    
    
    // Method to switch to the specified panel
    public void switchPanel(JPanel panel) {
        // this code handles the case where a customer presses back twice from selection -> cart -> product view
        if (Objects.nonNull(controller.getPreviousPanel()) && panel.equals(controller.getPreviousPanel())) {
            // check whether the panel and previousPanel are both cart. then set prevpanel to be product view.
            controller.setPreviousPanel(customerProductPanel);
        }
        getContentPane().removeAll(); // Remove all panels
        getContentPane().add(panel);  // Add the specified panel
        revalidate();
        repaint();
    }
       
}
