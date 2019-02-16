/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.DaoInterface;
import org.jooby.Jooby;

/**
 *
 * @author soupa868
 */
public class ProductModule extends Jooby {

    public ProductModule(DaoInterface dao) {
        get("/api/products", () -> dao.getProducts());
        get("/api/products/:id", (req) -> {
            int id = req.param("id").intValue();
            return dao.searchId(id);
        });
        get("/api/categories", () -> dao.getCategorys());
        get("/api/categories/:category", (req) -> {
            String id = req.param("category").value();
            return dao.filterSearch(id);
        });
    }
}
