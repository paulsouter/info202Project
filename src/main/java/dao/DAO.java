/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author paulo
 */
public class DAO implements DaoInterface {

    private Collection<Product> productList = new HashSet();
    private Collection<String> categorys = new HashSet();
    private Map<Integer, Product> products = new HashMap<>();

    @Override
    public Collection<Product> getProducts() {
        return productList;
    }

    @Override
    public void addProduct(Product product) {
       
            productList.add(product);
            products.put(product.getProductId(), product);
            categorys.add(product.getCategory());
            

    }

    @Override
    public Collection<String> getCategorys() {
        return categorys;
    }

    @Override
    public Collection<Product> filterSearch(String category) {
        HashSet<Product> result = new HashSet();
        if (!categorys.contains(category)) {
            return productList;
        }
        for (Product product : productList) {
            String cat = product.getCategory();
            if (cat == category) {
                result.add(product);
            }
        }
        return result;
    }

    @Override
    public Product searchId(int id) {
            return products.get(id);
    }

    @Override
    public void deleteProduct(Product product) {
        products.remove(product.getProductId());
        
        productList.remove(product);
    }

    public boolean inDatabase(int id) {
        if (id == searchId(id).getProductId()) {
            return true;
        } else {
            return false;
        }

    }
}
