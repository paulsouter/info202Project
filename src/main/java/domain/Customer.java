/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;
 
import java.util.ArrayList;

/**
 *
 * @author soupa868
 */
public class Customer {
    
    private Integer personId;
    private String username;
    private String password;
    private String firstName;
    private String surname;
    private String emailAddress;
    private String shippingAddress;
    private String creditCardDetails;
    private ArrayList<Sale> buyes;

    public Customer(int personId, String username, String password, String firstName, String surname, String emailAddress, String shippingAddress, String creditCardDetails) {
        this.personId = personId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.shippingAddress = shippingAddress;
        this.creditCardDetails = creditCardDetails;
    }

    public Customer(){
        
    }
    
    public ArrayList<Sale> getBuyts() {
        return buyes;
    }

    public void setBuyts(Sale buyts) {
        this.buyes = buyes;
    }
 
    public int getPersonId() {
        return personId;
    }
 
    public void setPersonId(int personId) {
        this.personId = personId;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getSurname() {
        return surname;
    }
 
    public void setSurname(String surname) {
        this.surname = surname;
    }
 
    public String getEmailAddress() {
        return emailAddress;
    }
 
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
 
    public String getShippingAddress() {
        return shippingAddress;
    }
 
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
 
    public String getCreditCardDetails() {
        return creditCardDetails;
    }
 
    public void setCreditCardDetails(String creditCardDetails) {
        this.creditCardDetails = creditCardDetails;
    }
 
    @Override
    public String toString() {
        return "Customer{" + "personId=" + personId + ", firstName=" + firstName + ", surname=" + surname + '}';
    }
    
}