/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1.DAO;

import com.mycompany.servlettask1.Entity.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Artur
 */
public class ProductServiceImpl implements ProductService{
    
    private String url;
    private String driverName;
    private String user;
    private String password;
    private Connection connection; 

    public ProductServiceImpl(String url, String user, String password, String driverName) {
        this.url = url;
        this.driverName = driverName;
        this.user = user;
        this.password = password;
        this.connection =createNewConnection();
    }
    
    private Connection createNewConnection(){
        try{
        Class.forName(getDriverName());
        Connection connection=DriverManager.getConnection(getUrl(), getUser(), getPassword());
        return connection;
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
    
    private boolean connectionIsReady(){
        try{
        return (this.connection!=null && !this.connection.isClosed());
        }
        catch(SQLException ex){
            return false;
        }
    }
    
    private Product mapProduct(ResultSet rs){
        try {
            return new Product(rs.getLong("id"),rs.getString("title") ,rs.getInt("price"));
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Product findById(long id){
        try {
            if (!connectionIsReady())
                this.connection=createNewConnection();
            if (!connectionIsReady())
                throw new SQLException("failed connect creation");
            PreparedStatement statement=connection.prepareStatement("Select * from product where id=?");
            statement.setLong(1, id);
            ResultSet resultSet=statement.executeQuery();
            resultSet.next();
            return mapProduct(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<Product> findAll(){
        try {
            if (!connectionIsReady())
                this.connection=createNewConnection();
            if (!connectionIsReady())
                throw new SQLException("failed connect creation");
            PreparedStatement statement=connection.prepareStatement("Select * from product");
            ResultSet resultSet=statement.executeQuery();
            List<Product> products=new ArrayList<>();
            while (resultSet.next()){
                products.add(mapProduct(resultSet));
            }
            return products;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public String getUrl() {
        return url;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
