package ClothingStoreGUI;

// FixedDiscount removes a set amount from the original product price

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FixedDiscount extends Discount{

    public FixedDiscount(BigDecimal amount) {
        super(amount);
    }
    
    @Override
    protected BigDecimal calcNewPrice(BigDecimal price) {
        BigDecimal newPrice = price.subtract(amount);
        return newPrice.setScale(2, RoundingMode.HALF_UP);
    }
    
    @Override
    public String cartString() {
        if (super.amount.scale() == 0) {
            return "$"+super.amount+" off"; // e.g. $15 off
        }
        return String.format("$%.2f off", super.amount); // e.g. $15.50 off
    }
    
//    // string formatting for viewCart function
//    public String cartString() {
//        // format without decimals if there isnt a cents value
//        if (super.amount.scale() == 0) {
//            return " ($"+super.amount+" off!)"; // e.g. ($15 off!)
//        }
//        return String.format(" ($%.2f off!)", super.amount); // e.g. ($15.50 off!)
//    }

}
