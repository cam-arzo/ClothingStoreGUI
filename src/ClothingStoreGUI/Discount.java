package ClothingStoreGUI;

import java.math.BigDecimal;

public abstract class Discount {
    protected BigDecimal amount;
    
    public Discount(BigDecimal amount) {
        this.amount = amount;
    }
    
    protected abstract BigDecimal calcNewPrice(BigDecimal price);
    
    // !! may need a way for database to know the ID to use for each discount
    
    // !! unsure if this is used
    // clean string without special viewCart formatting
    // used when modifying discounts in staff view
    public abstract String toCleanString();
    
}