package ClothingStoreGUI.Enums;

public enum Gender {
    UNISEX, MALE, FEMALE, NONE;

    private static final Gender[] VALUES = values();

    // convert int to enum
    public static Gender intToGender(int value) {
        if (value >= 0 && value < VALUES.length) {
            return VALUES[value];
        }
        return NONE;
    }
    
    public static String[] toStringArray() {
        return new String[]{"Unisex", "Male", "Female"};
    }
}