/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;
 
import java.math.BigDecimal;
import java.util.Objects;
import net.sf.oval.constraint.*;
 
/**
 *
 * @author soupa868
 */
public class Product {
    @NotNull(message = "ID must be provided.")
    private Integer productId;
    @NotNull(message = "Name must be provided.")
    @NotBlank(message = "Name must be provided.")
    @Length(min=2, message="Name must contain at least two characters.")
    private String name;
    private String description;
    @NotNull(message = "category must be provided.")
    private String category;
    @NotNull(message = "Price must be provided.")
    @NotNegative(message = "Price must be zero or greater.")
    private BigDecimal pricelist;
        @NotNull(message = "quantity must be provided.")
    @NotNegative(message = "quantity must be zero or greater.")
    private BigDecimal quantityInStock;

    public Product() {
    }
 
    public Product(Integer productId, String name, String description, String category, BigDecimal pricelist, BigDecimal quantityInStock) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.pricelist = pricelist;
        this.quantityInStock = quantityInStock;
    }
 
    public Integer getProductId() {
        return productId;
    }
 
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
 
    public String getCategory() {
        return category;
    }
 
    public void setCategory(String category) {
        this.category = category;
    }
 
    public BigDecimal getPricelist() {
        return pricelist;
    }
 
    public void setPricelist(BigDecimal pricelist) {
        this.pricelist = pricelist;
    }
 
    public BigDecimal getQuantityInStock() {
        return quantityInStock;
    }
 
    public void setQuantityInStock(BigDecimal quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
 
    @Override
    public String toString() {
        return "Product: " + "productId= " + productId + ", name= " + name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.productId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        return true;
    }
    
}