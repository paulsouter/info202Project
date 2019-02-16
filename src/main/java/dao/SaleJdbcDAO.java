package dao;

import domain.Customer;
import domain.Product;
import domain.Sale;
import domain.SaleItem;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleJdbcDAO implements SaleDAO {

    private static final String url = "jdbc:h2:tcp://localhost:9004/project;IFEXISTS=TRUE";

    @Override
    public void save(Sale sale) {
        
        sale.setStatus("hi");
        Connection con = JdbcConnection.getConnection(url);
        try {
            try (
                    PreparedStatement insertSaleStmt = con.prepareStatement(
                            "insert into sale (saledate, customerid) values (?,?)",
                            Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement insertSaleItemStmt = con.prepareStatement(
                            "insert into saleitem (quantity, price, productid, saleid) values (?,?,?,?)");
                    PreparedStatement updateProductStmt = con.prepareStatement(
                            "update product set quantityinstock = (?) where productid = (?)");) {

                // Since saving and sale involves multiple statements across
                // multiple tables we need to control the transaction ourselves
                // to ensure our DB remains consistent.
                //
                // Turn off auto-commit which effectively starts a new transaction.
                con.setAutoCommit(false);

                Customer customer = sale.getCustomer();

                // #### save the sale ### //
                // add a date to the sale if one doesn't already exist
                if (sale.getDate() == null) {
                    sale.setDate(new Date());
                }

                // convert sale date into to java.sql.Timestamp
                Date date = sale.getDate();
                Timestamp timestamp = new Timestamp(date.getTime());

                // ****
                // write code here that saves the timestamp and username in the
                // sale table using the insertSaleStmt statement.
                insertSaleStmt.setTimestamp(1, timestamp);
//                insertSaleStmt.setString(2, customer.get)
                insertSaleStmt.setInt(2, customer.getPersonId());
                insertSaleStmt.execute();
                // ****
                // get the auto-generated sale ID from the database
                ResultSet rs = insertSaleStmt.getGeneratedKeys();

                Integer saleId = null;

                if (rs.next()) {
                    saleId = rs.getInt(1);
                } else {
                    throw new DaoException("Problem getting generated Sale ID");
                }

                Collection<SaleItem> items = sale.getSaleItems();

                for (SaleItem item : items) {

                    Product product = item.getProduct();

                    // ****
                    // write code here that saves the sale item
                    // using the insertSaleItemStmt statement.
                    insertSaleItemStmt.setBigDecimal(1, item.getQuantityPurchased());
                    insertSaleItemStmt.setBigDecimal(2, item.getSalePrice());
                    insertSaleItemStmt.setInt(3, item.getProduct().getProductId());
                    insertSaleItemStmt.setInt(4,saleId);
                    insertSaleItemStmt.execute();
                    // ****
                    // ****
                    // write code here that updates the product quantity using
                    // the updateProductStmt statement.
                    BigDecimal num = product.getQuantityInStock().subtract(item.getQuantityPurchased());
                    updateProductStmt.setBigDecimal(1, num);
                    updateProductStmt.setInt(2, item.getProduct().getProductId());
                    updateProductStmt.execute();
                    // ****
                }

                // commit the transaction
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {

            Logger.getLogger(SaleJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);

            try {
                // something went wrong so rollback
                con.rollback();

                // turn auto-commit back on
                con.setAutoCommit(true);

                // and throw an exception to tell the user something bad happened
                throw new DaoException(ex.getMessage(), ex);
            } catch (SQLException ex1) {
                throw new DaoException(ex1.getMessage(), ex1);
            }

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(SaleJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Sale getSale(Integer id) {
        String sql = "select * from Sale where saleid = (?)";
        String sql2 = "select * from Saleitem where saleid = (?)";

        try (
                // get a connection to the database
                Connection dbCon = JdbcConnection.getConnection(url);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);
                PreparedStatement stmt2 = dbCon.prepareStatement(sql2);) {
            stmt.setInt(1, id);
            stmt2.setInt(1, id);
// execute the query
            ResultSet rs = stmt.executeQuery();
            ResultSet rs2 = stmt2.executeQuery();

//            Sale s = new Sale();
            if (rs.next()) {
                // iterate through the query results
                // get the data out of the query
                Integer ids = rs.getInt("saleid");
                Date date = rs.getTimestamp("saleDate");
                String status = rs.getString("status");
                Integer customerid = rs.getInt("customerid");

                Customer c = new CustomerDatabase().getCustomerid(customerid);
                Sale s = new Sale(id, date, status, c);

                while (rs2.next()) {
                    String quantity = rs.getString("quantity");
                    String price = rs.getString("price");
                    Integer productId = rs.getInt("proeductid");
                    Product p = new DatabaseManager().searchId(productId);
                    SaleItem i = new SaleItem(new BigDecimal(quantity), new BigDecimal(price), p, s);
                    s.addItem(i);
                }
                return s;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }
}
