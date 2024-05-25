package ClothingStoreGUI;

import java.math.BigDecimal;

public abstract class Discount {
    protected BigDecimal amount;
    
    public Discount(BigDecimal amount) {
        this.amount = amount;
    }
    
    protected abstract BigDecimal calcNewPrice(BigDecimal price);
    
    // !! may need a way for database to know the ID to use for each discount
    // 0: None, 1: Fixed, 2: Percent
    
    public abstract String cartString();
    
}