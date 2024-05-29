/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClothingStoreGUI;

import java.math.BigDecimal;

/**
 *
 * @author annek
 */
public class OrderProduct {
    private Product product;
    private String size;
    private int quantity;
    private BigDecimal totalPrice; // for this item. size * qty
    
    public OrderProduct(Product product) {
        this.product = product;
        this.totalPrice = this.product.getDiscountedPrice().multiply(new BigDecimal(this.quantity));
    }
    
    public OrderProduct(Product product, String size, int quantity) {
        this.product = product;
        this.size = size;
        this.quantity = quantity;
        this.totalPrice = this.product.getDiscountedPrice().multiply(new BigDecimal(this.quantity));
    }

    @Override
    // example output: Comfy Cotton T-Shirt, size M, [x2] - $59.98
    public String toString() {
        String out = "";
//        out += product.getName() + ", Size " + size + ", [x" + quantity + "] = $" + totalPrice; // total price function called so that it formats to 2dp
        out += String.format("%-40s %-20s %-10s $%-20.2f", product.getName(), "Size " + size, "[x" + quantity + "]", totalPrice);

        // print discount info e.g. (20% off!) if possible
        if (product.hasDiscount()) {
            out += " " + product.getDiscount().cartString();
        }
        return out;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        setTotalPrice();
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice() {
        this.totalPrice = this.product.getDiscountedPrice().multiply(new BigDecimal(this.quantity));
    }
}
