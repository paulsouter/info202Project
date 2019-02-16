/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.CustomerDaoInterface;
import dao.CustomerDatabase;
import dao.DaoInterface;
import dao.DatabaseManager;
import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.json.Gzon;

/**
 *
 * @author soupa868
 */
public class Server extends Jooby {

    private DaoInterface productDao = new DatabaseManager();
    private CustomerDaoInterface customerDao = new CustomerDatabase();

    public static void main(String[] args) throws Exception {
        System.out.println("\nStarting Server.");
        Server server = new Server();
        CompletableFuture.runAsync(() -> {
            server.start();
        });
        server.onStarted(() -> {
            System.out.println("\nPress Enter to stop the server.");
        });

// wait for user to hit the Enter key
        System.in.read();
        System.exit(0);
    }

    public Server() {
        port(4444);
        use(new Gzon());
        use(new ProductModule(productDao));
        use(new CustomerModule(customerDao));
        use(new ShoppingModule());
        use(new AssetModule());
        

    }
}
