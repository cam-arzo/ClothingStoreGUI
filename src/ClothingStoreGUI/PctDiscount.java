/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClothingStoreGUI;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author annek
 */
public class PctDiscount extends Discount {
    public PctDiscount(BigDecimal amount) {
        super(amount);
    }
    
    @Override
    protected BigDecimal calcNewPrice(BigDecimal price) {
        BigDecimal newPrice = price.subtract(price.multiply(amount.divide(new BigDecimal(100))));
        return newPrice.setScale(2, RoundingMode.HALF_UP);
    }
    
    @Override
    public String cartString() {
        if (super.amount.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            return "("+amount.intValue() +"% off!)"; // e.g. 15% off
        }
        return String.format("(%.2f%% off!)", super.amount); // e.g. 15.50% off
    }
}
