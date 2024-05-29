/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ClothingStoreGUI.Enums;

public enum Category {
    CASUAL("Casual"), SPORT("Sport"), FORMAL("Formal"), SLEEP("Sleep"), NONE("None");

    private static final Category[] VALUES = values();
    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static String[] toStringArray() {
        String[] array = new String[VALUES.length-1]; // skip over NONE
        for (int i = 0; i < VALUES.length-1; ++i) {
            array[i] = VALUES[i].displayName;
        }
        return array; // {"Casual", "Sport", "Formal", "Sleep"};
    }

    // convert int to enum
    public static Category intToCategory(int value) {
        if (value >= 0 && value < VALUES.length) {
            return VALUES[value];
        }
        return NONE;
    }
    
    // convert string name to enum constant
    public static Category fromDisplayName(String displayName) {
        for (Category category : Category.values()) {
            if (category.getDisplayName().equalsIgnoreCase(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No enum constant with display name " + displayName);
    }
    
    // Convert enum to int
    public int toInt() {
        return ordinal();
    }
    
    
}