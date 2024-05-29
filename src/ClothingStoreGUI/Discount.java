package ClothingStoreGUI;

import java.math.BigDecimal;

public abstract class Discount {
    protected BigDecimal amount;
    
    public Discount(BigDecimal amount) {
        this.amount = amount;
    }
    
    protected abstract BigDecimal calcNewPrice(BigDecimal price);
    
    public abstract String cartString();
    
}