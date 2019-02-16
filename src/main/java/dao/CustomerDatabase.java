/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author soupa868
 */
public class CustomerDatabase implements CustomerDaoInterface {

    private String url = "jdbc:h2:tcp://localhost:9004/project;IFEXISTS=TRUE";

    public CustomerDatabase() {

    }

    public CustomerDatabase(String url) {
        this.url = url;
    }

    @Override
    public void save(Customer customer) {
        String sql = "insert into customer (username, password, firstname, lastname, "
                + "email, address, creditcard) values (?,?,?,?,?,?,?)";

        try (
                // get connection to database
                Connection dbCon = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getPassword());
            stmt.setString(3, customer.getFirstName());
            stmt.setString(4, customer.getSurname());
            stmt.setString(5, customer.getEmailAddress());
            stmt.setString(6, customer.getShippingAddress());
            stmt.setString(7, customer.getCreditCardDetails());
            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Customer getCustomer(String username) {
        String sql = "select * from customer where username = (?)";

        try (
                // get a connection to the database
                Connection dbCon = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, username);
// execute the query
            ResultSet rs = stmt.executeQuery();
            Customer c = new Customer();
            if (rs.next()) {
                // iterate through the query results
                // get the data out of the query
                Integer id = rs.getInt("customerid");
                String username1 = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String creditCard = rs.getString("creditCard");

                // use the data to create a student object
                c = new Customer(id, username1, password, firstName, lastName, email, address, creditCard);
                return c;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Customer getCustomerid(Integer id) {
        String sql = "select * from customer where customerid = (?)";

        try (
                // get a connection to the database
                Connection dbCon = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setInt(1, id);
// execute the query
            ResultSet rs = stmt.executeQuery();
            Customer c = new Customer();
            if (rs.next()) {
                String username1 = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String creditCard = rs.getString("creditCard");

                // use the data to create a student object
                c = new Customer(id, username1, password, firstName, lastName, email, address, creditCard);
                return c;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean validateCredentials(String username, String password) {
        String sql = "select username, password from customer where username = (?)";

        try (
                // get a connection to the database
                Connection dbCon = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, username);
// execute the query
            ResultSet rs = stmt.executeQuery();

            // iterate through the query results
            // get the data out of the query
            String username1 = rs.getString("username");
            String password1 = rs.getString("password");

            if (username1 == username && password1 == password) {
                return true;
            }

        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
        return false;
    }
}
