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
        // price - discount amount = new price
        BigDecimal newPrice = price.subtract(amount);
        return newPrice.setScale(2, RoundingMode.HALF_UP);
    }
    
    @Override
    public String cartString() {
        // how the discount displays in cart panel
        if (super.amount.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            return String.format("($%.0f off!)", super.amount); // e.g. $15 off
        }
        return String.format("($%.2f off!)", super.amount); // e.g. $15.50 off
    }

}
