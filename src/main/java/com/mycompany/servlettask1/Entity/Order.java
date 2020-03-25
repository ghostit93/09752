/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1.Entity;

import java.util.List;

/**
 *
 * @author Artur
 */
public class Order {
    
    private long id;
    private List<Product> products;
    private int price;
    private User user;

    public Order(int price) {
        this.price = price;
    }
    
    public Order(long id,int price) {
        this.price = price;
        this.id=id;
    }

    public Order(long id, List<Product> products, int price) {
        this.id = id;
        this.products = products;
        this.price = price;
    }

    public Order(List<Product> products, int price, User user) {
        this.id = id;
        this.products = products;
        this.price = price;
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
}
