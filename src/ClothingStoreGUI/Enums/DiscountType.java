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
    NONE("None"), FIXED("Fixed"), PERCENT("Percent");
    
    private static final DiscountType[] VALUES = values();
    private final String displayName;

    DiscountType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static String[] toStringArray() {
        String[] array = new String[VALUES.length];
        for (int i = 0; i < VALUES.length; ++i) {
            array[i] = VALUES[i].displayName;
        }
        return array; // {"None", "Fixed", "Percent"};
    }

    // convert int to enum
    public static DiscountType intToDiscount(int value) {
        if (value >= 0 && value < VALUES.length) {
            return VALUES[value];
        }
        return NONE;
    }
    
    public static DiscountType fromDisplayName(String displayName) {
        for (DiscountType discountType : DiscountType.values()) {
            if (discountType.getDisplayName().equalsIgnoreCase(displayName)) {
                return discountType;
            }
        }
        throw new IllegalArgumentException("No enum constant with display name " + displayName);
    }
}
