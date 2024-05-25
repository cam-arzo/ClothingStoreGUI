/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClothingStoreGUI;

import java.math.BigDecimal;

public class ClothingItem extends Product {
    
    // contains unique size system
    private static final String[] CLOTHING_SIZES = {"XS", "S", "M", "L", "XL", "XXL"};
    
    // makes ClothingItem with placeholder data
    public ClothingItem() {
        super();
        setSizes(getSizeSystem());
    }
    
    public ClothingItem(int id, String name, boolean available, BigDecimal price, int gender, int category, Discount discount) {
        super(id, name, available, price, gender, category, discount);
        setSizes(getSizeSystem());
    }

    
    @Override
    public String[] getSizeSystem() {
        return CLOTHING_SIZES;
    }
    
}
