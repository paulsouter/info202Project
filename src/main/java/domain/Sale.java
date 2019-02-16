/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;
 
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author soupa868
 */
public class Sale {
    
    private int saleId;
    private Date date;
    private String status;
    private Customer customer;
    private ArrayList<SaleItem> items;

    public Sale(int saleId, Date date, String status, Customer customer, ArrayList<SaleItem> saleItems) {
        this.saleId = saleId;
        this.date = date;
        this.status = status;
        this.customer = customer;
        this.items = saleItems;
    }
    public Sale(int saleId, Date date, String status, Customer customer) {
        this.saleId = saleId;
        this.date = date;
        this.status = status;
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<SaleItem> getSaleItems() {
        return items;
    }

    public void setItems(ArrayList<SaleItem> saleItems) {
        this.items = saleItems;
    }
    public void addItem(SaleItem saleItem){
        this.items.add(saleItem);
    }
 
    public int getSaleId() {
        return saleId;
    }
 
    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }
 
    public Date getDate() {
        return date;
    }
 
    public void setDate(Date date) {
        this.date = date;
    }
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String toString(){
        String result = "";
        result+= Integer.toString(saleId);
        result+="\t" + date + "\t" + status +"\t" + customer.toString();
        for(SaleItem i: items){
            result+="\t" + i.toString();
        }
        return result;
    }
    
}
