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
public class Document {
    
    private long id;
    private String name;
    private String text;

    public Document(long id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }
    
    public Document(String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
