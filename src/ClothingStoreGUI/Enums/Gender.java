package ClothingStoreGUI.Enums;

public enum Gender {
    UNISEX("Unisex"), MALE("Male"), FEMALE("Female"), NONE("None");

    private static final Gender[] VALUES = values();
    private final String displayName;

    Gender(String displayName) {
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
        return array; // {"Unisex", "Male", "Female"};
    }

    // convert int to enum
    public static Gender intToGender(int value) {
        if (value >= 0 && value < VALUES.length) {
            return VALUES[value];
        }
        return NONE;
    }
    
    // convert string name to enum constant
    public static Gender fromDisplayName(String displayName) {
        for (Gender gender : VALUES) {
            if (gender.getDisplayName().equalsIgnoreCase(displayName)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("No enum constant with display name " + displayName);
    }
    
    // Convert enum to int
    public int toInt() {
        return ordinal();
    }
    
}