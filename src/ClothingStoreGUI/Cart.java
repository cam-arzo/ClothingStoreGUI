/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClothingStoreGUI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author annek
 */
public class Cart {
    public List<OrderProduct> cartProducts = new ArrayList<>();
    public int numItems = 0;
    public BigDecimal totalPrice = new BigDecimal(0);
    
    public Cart() {    
    }
    
    // adds item to cart
    public void addItem(OrderProduct product) {
        for (OrderProduct existingProduct : cartProducts) {
            if (existingProduct.getProduct().equals(product.getProduct()) && 
                existingProduct.getSize().equals(product.getSize())) {
                // If a similar product exists, update its quantity
                existingProduct.setQuantity(existingProduct.getQuantity() + product.getQuantity());
                setNumItems();
                setTotalPrice();
                return; // Exit the method after updating the quantity
            }
        }
        // if there is no similar product, add the product
        this.cartProducts.add(product);
        this.numItems += product.getQuantity();
        this.totalPrice.add(product.getTotalPrice());
    }
    
    @Override
    public String toString() {
        String out = "";
        int i = 1;
        for (OrderProduct selectedProduct : cartProducts) {
           out += "("+i+") "+selectedProduct+"\n";
           i++;
        }
        out += "Total price: $"+String.format("%.2f", totalPrice); // format to 2dp
        return out;
    }

    public void removeOrderProduct(int index) {
        cartProducts.remove(index); // remove product
        setNumItems(); // update total no of items
        setTotalPrice(); // update total price
    }
    
    public void updateCart(int index, OrderProduct newProduct) {
        System.out.println("updating "+newProduct);
        cartProducts.set(index, newProduct);
        setNumItems(); // update total no of items
        System.out.println("numitems is "+numItems);
        setTotalPrice(); // update total price
        System.out.println(String.format("total price is $%.2f",totalPrice));
    }
    
    public int getNumItems() {
        return numItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice() {
        BigDecimal sum = new BigDecimal(0);
        for (OrderProduct product : cartProducts) {
            sum.add(product.getTotalPrice());
        }
        this.totalPrice = sum;
    }

    public void setNumItems() {
        int sum = 0;
        for (OrderProduct product : cartProducts) {
            sum += product.getQuantity();
        }
        this.numItems = sum;
    }
}
