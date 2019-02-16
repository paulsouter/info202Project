/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;
 
import java.math.BigDecimal;
 
/**
 *
 * @author soupa868
 */
public class SaleItem {
 
 
    private BigDecimal quantityPurchased;
    private BigDecimal salePrice;
    private Product product;
    private Sale sale;

    
    public SaleItem(BigDecimal quantity, BigDecimal price, Product product, Sale sale){
        this.quantityPurchased = quantity;
        this.salePrice = price;
        this.sale = sale;
        this.product = product;
    }
        public BigDecimal getQuantityPurchased() {
        return quantityPurchased;
    }
 
    public void setQuantityPurchased(BigDecimal quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }
 
    public BigDecimal getSalePrice() {
        return salePrice;
    }
 
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
        public Product getProduct() {
        return product;
    }

    public Sale getSale() {
        return sale;
    }
    public String toString(){
        String result = product.getName() + "\t" + quantityPurchased.toString() +"\t"+ salePrice.toString() + "\t" + (quantityPurchased.multiply(salePrice)).toString();
        return result;
    }
    
}