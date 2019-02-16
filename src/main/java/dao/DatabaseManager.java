/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author soupa868
 */
public class DatabaseManager implements DaoInterface {

    private String url = "jdbc:h2:tcp://localhost:9004/project;IFEXISTS=TRUE";

    public DatabaseManager() {

    }

    public DatabaseManager(String url) {
        this.url = url;
    }

    @Override
    public void addProduct(Product product) {
        String sql = "merge into product(productid, productName, description, "
                + "category, pricelist, quantityInStock) values (?,?,?,?,?,?)";

        try (
                // get connection to database
                Connection dbCon = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            // copy the data from the student domain object into the SQL parameters
            stmt.setInt(1, product.getProductId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setString(4, product.getCategory());
            stmt.setBigDecimal(5, product.getPricelist());
            stmt.setBigDecimal(6, product.getQuantityInStock());
            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new DaoException(ex.getMessage(), ex);
        }

    }

    @Override
    public void deleteProduct(Product product) {
        String sql = "delete from product where productid = ?";

        try (
                // get connection to database
                Connection connection = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            // execute the query
            stmt.setInt(1, product.getProductId());
            stmt.executeUpdate();

        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Product> filterSearch(String category) {
        String sql = "select * from product where category = (?)";

        try (
                // get connection to database
                Connection connection = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            // execute the query
            stmt.setString(1, category);
            Collection<Product> products = new HashSet();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("productid");
                String name = rs.getString("productName");
                String description = rs.getString("description");
                String categoryp = rs.getString("category");
                BigDecimal priceList = rs.getBigDecimal("priceList");
                BigDecimal quantity = rs.getBigDecimal("quantityInStock");

                // use the data to create a student object
                Product p = new Product(id, name, description, categoryp, priceList, quantity);

                products.add(p);
            }
            return products;
        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<String> getCategorys() {
        String sql = "select distinct category from product";

        try (
                // get connection to database
                Connection connection = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            // execute the query
            ResultSet rs = stmt.executeQuery();

            Collection<String> cat = new HashSet();
            while (rs.next()) {
                String name = rs.getString("category");
                cat.add(name);

            }
            return cat;
        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Product> getProducts() {
        String sql = "select * from product order by productid";

        try (
                // get a connection to the database
                Connection dbCon = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            // execute the query
            ResultSet rs = stmt.executeQuery();

            // Using a List to preserve the order in which the data was returned from the query.
            List<Product> products = new ArrayList<>();

            // iterate through the query results
            while (rs.next()) {

                // get the data out of the query
                Integer id = rs.getInt("productid");
                String name = rs.getString("productName");
                String description = rs.getString("description");
                String category = rs.getString("category");
                BigDecimal priceList = rs.getBigDecimal("priceList");
                BigDecimal quantity = rs.getBigDecimal("quantityInStock");

                // use the data to create a student object
                Product p = new Product(id, name, description, category, priceList, quantity);

                // and put it in the collection
                products.add(p);
            }

            return products;

        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Product searchId(int id) {
        String sql = "select * from product where productid = (?)";

        try (
                // get connection to database
                Connection connection = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = connection.prepareStatement(sql);) {
            // execute the query
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Integer idp = rs.getInt("productid");
                String name = rs.getString("productName");
                String description = rs.getString("description");
                String category = rs.getString("category");
                BigDecimal priceList = rs.getBigDecimal("priceList");
                BigDecimal quantity = rs.getBigDecimal("quantityInStock");

                // use the data to create a student object
                Product p = new Product(idp, name, description, category, priceList, quantity);
                return p;

            } else {
                return null;
            }

        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean inDatabase(int id) {
        if (searchId(id) != null) {
            return true;
        } else {
            return false;
        }

    }

}
