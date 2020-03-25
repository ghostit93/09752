/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1.Entity;

/**
 *
 * @author Artur
 */
public class Product {
    
    private long id;
    private String name;
    private int value;

    public Product(long id, String name, int value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }    

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
}
