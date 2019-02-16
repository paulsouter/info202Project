/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;

/**
 *
 * @author soupa868
 */
public interface CustomerDaoInterface {

    void save(Customer customer);

    Customer getCustomer(String username);

    Customer getCustomerid(Integer id);

    Boolean validateCredentials(String username, String password);

}
