package ClothingStoreGUI;

import java.math.BigDecimal;

public class Order {
    
    private int id;
    private int quantity;
    private BigDecimal total_price;
    
    public Order(int id, int quantity, BigDecimal total_price) {
        this.id = id;
        this.quantity = quantity;
        this.total_price = total_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return total_price;
    }

    public void setTotalPrice(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
}
