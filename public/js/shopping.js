/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


"use strict";
class SaleItem {

    constructor(product, quantity) {
        // only set the fields if we have a valid product
        if (product) {
            this.product = product;
            this.quantityPurchased = quantity;
            this.salePrice = product.pricelist;
        }
    }

    getItemTotal() {
        return this.salePrice * this.quantityPurchased;
    }
    getProduct() {
        return this.product;

    }
    getQuantity() {
        return this.quantityPurchased;
    }
    changeQuantity(quantity) {
        this.quantityPurchased = this.quantityPurchased + quantity;
    }
}

class ShoppingCart {

    constructor() {
        this.items = new Array();
    }

    reconstruct(sessionData) {
        for (let item of sessionData.items) {
            this.addItem(Object.assign(new SaleItem(), item));
        }
    }

    getItems() {
        return this.items;
    }

    addItem(item) {
//        alert(this.items.length)
        if (this.items.length !== 0) {
            for (let check of this.items) {
                if (item.getProduct().productId === check.getProduct().productId) {
                    if (check.getQuantity() + item.getQuantity() <= 0) {
                        alert("can't have a negative quantity, currently qunatity is " + check.getQuantity());
                        return;
                    } else {
                        check.changeQuantity(item.getQuantity());
                        return;
                    }
                }
            }
        }
//        alert(item.getProduct().quantityInStock - item.getQuantity())
        if (item.getProduct().quantityInStock - item.getQuantity() >= 0) {
          
            this.items.push(item);
        }else{
              alert("you have ordered more then we have at the moment");
            return;
        }
    }

    setCustomer(customer) {
        this.customer = customer;
    }

    getTotal() {
        let total = 0;
        for (let item of this.items) {
            total += item.getItemTotal();
        }
        return total;
    }

}



var module = angular.module('ShoppingApp', ['ngResource', 'ngStorage']);

module.factory('productDAO', function ($resource) {
    return $resource('/api/products/:id');
});

module.factory('saveDAO', function ($resource) {
    return $resource('/api/save/');
});

module.factory('cart', function ($sessionStorage) {
    let cart = new ShoppingCart();

    // is the cart in the session storage?
    if ($sessionStorage.cart) {

        // reconstruct the cart from the session data
        cart.reconstruct($sessionStorage.cart);
    }

    return cart;
});

module.controller('CartController', function (cart, saveDAO, $sessionStorage, $window) {
    this.items = cart.getItems();
    this.total = cart.getTotal();
    this.selectedProduct = $sessionStorage.product;
    this.storeProduct = function (product) {
        $sessionStorage.product = product;
        $window.location.href = './quantityPurchase.html';
        this.selectedProduct = $sessionStorage.product;
    };
    this.addToCart = function (quantity) {
        let saleItem = new SaleItem($sessionStorage.product, quantity);
        cart.addItem(saleItem);
        $sessionStorage.cart = cart;
        $window.location.href = './viewProducts.html';
    };

    this.checkOutCart = function () {
        cart.setCustomer($sessionStorage.customer);
        saveDAO.save(null, cart);
        delete $sessionStorage.cart;
        $window.location.href = "./checkout.html";
    };

});




module.controller('ProductController', function (productDAO, categoryDAO) {
    this.products = productDAO.query();
    // load the categories
    this.categories = categoryDAO.query();
    // click handler for the category filter buttons
    this.getProducts = function () {
        this.products = productDAO.query();
    };

    this.selectCategory = function (selectedCat) {
        this.products = categoryDAO.query({"cat": selectedCat});

    };

});

module.factory('categoryDAO', function ($resource) {
    return $resource('/api/categories/:cat');
});

module.factory('registerDAO', function ($resource) {
    return $resource('/api/register/');
});
module.factory('signInDAO', function ($resource) {
    return $resource('/api/customers/:username');
});

module.controller('registerCustomer', function (registerDAO, signInDAO, $sessionStorage, $window) {
    this.signedIn = false;
    this.welcome;

    this.registerCustomer = function (customer) {
        registerDAO.save(null, customer);
        console.log(customer);
        $sessionStorage.customer = customer;
        $window.location.href = ".";
    };
    this.signInMessage = "Please sign in to continue.";

    // alias 'this' so that we can access it inside callback functions
    let ctrl = this;
    this.signIn = function (username, password) {
// get customer from web service
        signInDAO.get({'username': username},
// success
                function (customer) {
// also store the retrieved customer
                    $sessionStorage.customer = customer;
// redirect to home
                    $window.location.href = '.';
                },
// fail
                function () {
                    ctrl.signInMessage = 'Sign in failed. Please try again.';
                }
        );
    };

    this.checkSignIn = function () {
        if ($sessionStorage.customer) {
            ctrl.signedIn = true;
            ctrl.welcome = "Hello " + $sessionStorage.customer.username;
        }
    };

    this.signOut = function () {
        $sessionStorage.$reset();
        ctrl.signIn = false;
        $window.location.href = '.';
    };
});


