package ClothingStoreGUI;

import ClothingStoreGUI.Enums.Category;
import ClothingStoreGUI.Enums.DiscountType;
import ClothingStoreGUI.Enums.Gender;
import ClothingStoreGUI.Enums.ProductType;
import java.math.BigDecimal;

// when purchased, a 'SelectedProduct' class is used to store user's choices (size, amount)

public abstract class Product {
    
    // unique ID used in the database for each product
    private int id;
    // product has one main unique name
    private String name;
    // product is either available or unavailable
    private boolean available;
    // category and gender are stored in enums
    private Category category;
    private Gender gender;
    // !! stored as a bigdecimal for the correct precision
    // !! stores as numeric(6,2) in database, so it should have 2dp
    // !! use price.setScale(2, BigDecimal.ROUND_HALF_UP) to round to 2dp
    // !! watch out for 999999.99 rounding up to 1000000.00 and causing errors
    private BigDecimal price;
    private Discount discount;
    private DiscountType discountType;
    private BigDecimal discountedPrice;
    // a unique size system exists for clothes & shoes
    private String[] sizes;
    private ProductType productType;
      

    // new product with placeholders
    public Product() {
        this.id = -1;
        this.name = "Placeholder Name";
        this.available = false;
        this.price = new BigDecimal(10.00);
        this.discount = null;
        this.gender = Gender.UNISEX;
        this.category = Category.CASUAL;
    }
    
    // when adding product you only need to provide info unique to that product
    // e.g. sizes are the same across all clothing
    
    // !! should this be changed to factory pattern?
    
    public Product(int id, String name, boolean available, BigDecimal price, Gender gender, Category category, Discount discount, DiscountType discountType, ProductType productType) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.price = price;
        this.gender = gender;
        this.category = category;
        this.discount = discount;
        this.discountType = discountType;
        this.productType = productType;
        setDiscountedPrice(discount);
        // sizes are set in the product subclass
    }
    
    // Used when printing each product in the buy menu.
    public String toStringArray() {
        String out="";
        
        // label as unlisted if unavailable
        if (this.available == false) {
            out += "(Unlisted) ";
        }
        
        // if discount exists, show discounted & original price 
        if (discount != null) {
            // print name, discount price, old price
            out += String.format("%s - $%.2f (discounted from $%.2f!)", this.name, discount.calcNewPrice(price), this.price);
        } else {
            // print name and price (no discount)
            out += String.format("%s - $%.2f", this.name, this.price);
        }
        
        
        return out;
    }
    
    // SETTERS AND GETTERS
    
    // each product class has a unique size system
    abstract String[] getSizeSystem();

    public void setId(int id) {
        this.id = id;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
    
    // each product class has a type
    public String getType() {
        return this.getClass().getSimpleName();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String isAvailableString() { // formatted as String for the combo box
        if (available) {
            return "True";
        } else {
            return "False";
        }
    }
    
    public boolean isAvailable() { // formatted as String for the combo box
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean hasDiscount() {
        return (discount != null);
    }
    
    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
        setDiscountedPrice(discount); // update the discounted price
    }

    public void setDiscountedPrice(Discount discount) {
        if (discount == null) {
            this.discountedPrice = price;
        } else {
            this.discountedPrice = discount.calcNewPrice(this.price);
        }
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public Gender getGender() {
        return gender;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        setDiscountedPrice(this.discount); // update the discounted price
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String[] getSizes() {
        return sizes;
    }

    public void setSizes(String[] sizes) {
        this.sizes = sizes;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public ProductType getProductType() {
        return productType;
    }
    
}

