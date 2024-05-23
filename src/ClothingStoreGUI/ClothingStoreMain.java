package ClothingStoreGUI;

import java.util.Scanner;

public class ClothingStoreMain {
    
    public static void main(String[] args) {
        
        // Create main components for MVCD (MVC + Database)
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller();
        Database database = new Database();
        
        // Use setter injection to share references to parts of MVC
        view.setController(controller);  // View must attach GUI inputs to the controller
        model.setDatabase(database);  // Model needs database access for calculations / operations
        controller.setView(view);  // Controller is responsible for switching between views
        // !! may need to add more setters
        
        // Start database, add tables if they don't exist
        database.setup();
        
        // Create panels, switch to starting panel, show view
        view.setup(controller);
        
        
        // !! used for debugging
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter SQL table: ");
            String str = scanner.nextLine();
            database.previewTable(str);
            
        }
        
    }
    
}
