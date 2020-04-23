/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1.DAO;

import com.mycompany.servlettask1.Entity.Order;
import com.mycompany.servlettask1.Entity.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Artur
 */
public class OrderServiceImpl implements OrderService{
    
    private String url;
    private String driverName;
    private String user;
    private String password;
    private Connection connection;
    private ProductService productService;
    private UserService userService;

    public OrderServiceImpl(String url, String user, String password, String driverName,ProductService productService,UserService userService) {
        this.url = url;
        this.driverName = driverName;
        this.user = user;
        this.password = password;
        this.connection = createNewConnection();
        this.productService=productService;
        this.userService=userService;
    }

    @Override
    public Order save(Order order) {
        try {
            if (!connectionIsReady())
                this.connection=createNewConnection();
            if (!connectionIsReady())
                throw new SQLException("failed connect creation");
            PreparedStatement statement=connection.prepareStatement("Insert into orderContent (price,client_id) values(?,?)",Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, order.getPrice());
            statement.setLong(2, order.getUser().getId());
            if (statement.executeUpdate()==0)
                throw new SQLException("Insert statement was failed");
            ResultSet generatedKeys=statement.getGeneratedKeys();
            if (generatedKeys.next()){
                order.setId(generatedKeys.getLong(1));
                saveProducts(order);
                return order;
            }else
                return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private void saveProducts(Order order) throws SQLException{
        for (Product product:order.getProducts()){
            if (!connectionIsReady())
                this.connection=createNewConnection();
            if (!connectionIsReady())
                throw new SQLException("failed connect creation");
            PreparedStatement statement=connection.prepareStatement("Insert into order_product (order_id,product_id) values(?,?)",Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getId());
            statement.setLong(2, product.getId());
            if (statement.executeUpdate()==0)
                throw new SQLException("Insert statement was failed");
        }
    }

    @Override
    public List<Order> findAll() {
        try {
            if (!connectionIsReady())
                this.connection=createNewConnection();
            if (!connectionIsReady())
                throw new SQLException("failed connect creation");
            List<Product> products=new ArrayList<>();
            PreparedStatement statement=connection.prepareStatement("Select * from orderContent");
            ResultSet resultSet=statement.executeQuery();
            List<Order> orders=new ArrayList<>();
            while (resultSet.next()){
                Order order=mapOrder(resultSet);
                if (order!=null)
                    orders.add(order);
            }
            return orders;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
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
    
    private Order mapOrder(ResultSet rs){
        try {
            Order order= new Order(rs.getLong("id"),rs.getInt("price"));
            order.setProducts(getProductsByOrderId(order.getId()));
            order.setUser(userService.findById(rs.getLong("client_id")));
            return order;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<Product> getProductsByOrderId(long id){
        try {
            if (!connectionIsReady())
                this.connection=createNewConnection();
            if (!connectionIsReady())
                throw new SQLException("failed connect creation");
            List<Product> products=new ArrayList<>();
            PreparedStatement statement=connection.prepareStatement("Select * from order_product where order_id=?");
            statement.setLong(1, id);
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                long productId=resultSet.getLong("product_id");
                Product product=productService.findById(productId);
                if (product!=null)
                    products.add(product);
            }
            return products;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
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
