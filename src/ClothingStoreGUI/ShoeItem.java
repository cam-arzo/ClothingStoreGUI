package ClothingStoreGUI;

import ClothingStoreGUI.Enums.Category;
import ClothingStoreGUI.Enums.DiscountType;
import ClothingStoreGUI.Enums.Gender;
import ClothingStoreGUI.Enums.ProductType;
import java.math.BigDecimal;

public class ShoeItem extends Product {
    // contains unique size system
    private static final String[] MALE_SHOE_SIZES = {"4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    private static final String[] FEMALE_SHOE_SIZES = {"5", "6", "7", "8", "9", "10", "11", "12"};

    // makes ShoeItem with placeholder data
    public ShoeItem() {
        super();
        setSizes(getSizeSystem());
    }
    
    public ShoeItem(int id, String name, boolean available, BigDecimal price, Gender gender, Category category, Discount discount, DiscountType discountType) {
        super(id, name, available, price, gender, category, discount, discountType, ProductType.SHOES);
        setSizes(getSizeSystem());
    }
    
    // uses female shoe size ONLY for female shoes
    // uses male shoe size for other shoes (including unisex)
    @Override
    public String[] getSizeSystem() {
        if (getGender() == Gender.FEMALE)
        {
            return FEMALE_SHOE_SIZES;
        }
        return MALE_SHOE_SIZES;
    }
    
}
