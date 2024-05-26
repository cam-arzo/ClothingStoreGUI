/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ClothingStoreGUI.Enums;

/**
 *
 * @author annek
     */
public enum DiscountType {
    NONE, FIXED, PERCENT;
    
    private static final DiscountType[] VALUES = values();

    // convert int to enum
    public static DiscountType intToDiscount(int value) {
        if (value >= 0 && value < VALUES.length) {
            return VALUES[value];
        }
        return NONE;
    }
}
