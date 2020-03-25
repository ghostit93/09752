/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1.DAO;

import com.mycompany.servlettask1.Entity.User;
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
public class UserServiceImpl implements UserService{
    
    private String url;
    private String driverName;
    private String user;
    private String password;
    private Connection connection;        

    public UserServiceImpl(String url, String user, String password, String driverName) {
           this.driverName=driverName;
           this.url=url;
           this.user=user;
           this.password=password;
           this.connection=createNewConnection(); 
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

    @Override
    public void delete(long id) {
      //!!!  
    }

    @Override
    public User save(User user) {
        try {
            if (!connectionIsReady())
                this.connection=createNewConnection();
            if (!connectionIsReady())
                throw new SQLException("failed connect creation");
            PreparedStatement statement=connection.prepareStatement("Insert into client (login,password) values(?,?)",Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            if (statement.executeUpdate()==0)
                throw new SQLException("Insert statement was failed");
            user.setId(statement.getGeneratedKeys().getLong(1));
            return user;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public User findById(long id) {
        try {
            if (!connectionIsReady())
                this.connection=createNewConnection();
            if (!connectionIsReady())
                throw new SQLException("failed connect creation");
            PreparedStatement statement=connection.prepareStatement("Select * from client where id=?");
            statement.setLong(1, id);
            ResultSet resultSet=statement.executeQuery();
            resultSet.next();
            return mapUser(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<User> findByLogin(String login) {
        try {
            if (!connectionIsReady())
                this.connection=createNewConnection();
            if (!connectionIsReady())
                throw new SQLException("failed connect creation");
            PreparedStatement statement=connection.prepareStatement("Select * from client where login=?");
            statement.setString(1, login);
            ResultSet resultSet=statement.executeQuery();
            List<User> users=new ArrayList<>();
            while (resultSet.next())
            {
                users.add(mapUser(resultSet));
            }
            return users;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayList<User>();
        }
    }
    
    private User mapUser(ResultSet rs){
        try {
            return new User(rs.getLong("id"), rs.getString("login"), rs.getString("password"));
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
