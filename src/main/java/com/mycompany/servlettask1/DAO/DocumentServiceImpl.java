/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1.DAO;

import com.mycompany.servlettask1.Entity.Document;
import com.mycompany.servlettask1.Entity.Order;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Artur
 */
public class DocumentServiceImpl implements  DocumentService{
    
    private String url;
    private String driverName;
    private String user;
    private String password;
    private Connection connection;

    public DocumentServiceImpl(String url, String driverName, String user, String password) {
        this.url = url;
        this.driverName = driverName;
        this.user = user;
        this.password = password;
    }

    @Override
    public Document save(Document document) {
         try {
            if (!connectionIsReady())
                this.connection=createNewConnection();
            if (!connectionIsReady())
                throw new SQLException("failed connect creation");
            PreparedStatement statement=connection.prepareStatement("Insert into document (name,text) values(?,?)",Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, document.getName());
            statement.setString(2, document.getText());
            if (statement.executeUpdate()==0)
                throw new SQLException("Insert statement was failed");
            ResultSet generatedKeys=statement.getGeneratedKeys();
            if (generatedKeys.next()){
                document.setId(generatedKeys.getLong(1));
                return document;
            }else
                return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Document findById(long id){
        try {
            if (!connectionIsReady())
                this.connection=createNewConnection();
            if (!connectionIsReady())
                throw new SQLException("failed connect creation");
            PreparedStatement statement=connection.prepareStatement("Select * from document where id=?");
            statement.setLong(1, id);
            ResultSet resultSet=statement.executeQuery();
            resultSet.next();
            return mapDocument(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
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
    
    private Document mapDocument(ResultSet rs){
        try {
            Document document= new Document(rs.getLong("id"),rs.getString("name"),rs.getString("text"));
            return document;
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
