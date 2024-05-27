/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ClothingStoreGUI.Enums;

public enum Category {
    CASUAL, SPORT, FORMAL, SLEEP, NONE;

    private static final Category[] VALUES = values();

    // convert int to enum
    public static Category intToCategory(int value) {
        if (value >= 0 && value < VALUES.length) {
            return VALUES[value];
        }
        return NONE;
    }
    
    public static String[] toStringArray() {
        return new String[]{"Casual", "Sport", "Formal", "Sleep"};
    }
}