package ClothingStoreGUI.Enums;

public enum ProductType {
    CLOTHING, SHOES;
    
    private static final ProductType[] VALUES = values();

    // convert int to enum
    public static ProductType intToType(int value) {
        if (value >= 0 && value < VALUES.length) {
            return VALUES[value];
        }
        return CLOTHING;
    }
    
    public static String[] toStringArray() {
        return new String[]{"Clothing", "Shoes"};
    }
}
