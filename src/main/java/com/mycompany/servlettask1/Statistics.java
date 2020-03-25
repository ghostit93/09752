/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1;

import com.mycompany.servlettask1.DAO.OrderService;
import com.mycompany.servlettask1.DAO.OrderServiceImpl;
import com.mycompany.servlettask1.DAO.ProductService;
import com.mycompany.servlettask1.DAO.ProductServiceImpl;
import com.mycompany.servlettask1.DAO.UserService;
import com.mycompany.servlettask1.DAO.UserServiceImpl;
import com.mycompany.servlettask1.Entity.Order;
import com.mycompany.servlettask1.Entity.Product;
import com.mycompany.servlettask1.Gistogram.Gistogram;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Artur
 */
@WebServlet(value = "/gistogram")
public class Statistics extends HttpServlet{
    
    private UserService userService=new UserServiceImpl("jdbc:postgresql://localhost:5432/postgres2", "postgres", "root", "org.postgresql.Driver");
    private ProductService productService=new ProductServiceImpl("jdbc:postgresql://localhost:5432/postgres2", "postgres", "root", "org.postgresql.Driver");
    private OrderService orderService=new OrderServiceImpl("jdbc:postgresql://localhost:5432/postgres2", "postgres", "root", "org.postgresql.Driver",productService,userService);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders=orderService.findAll();
        Map<String,Integer> productCounter=new HashMap<>();
        for (Order order:orders){
            for (Product product:order.getProducts()){
                if (productCounter.containsKey(product.getName())){
                    productCounter.replace(product.getName(), productCounter.get(product.getName())+1);
                }else{
                    productCounter.put(product.getName(), new Integer(1));
                }
            }
        }
        Gistogram gistogram=new Gistogram(productCounter);
        BufferedImage image=gistogram.getBufferedImage(800,800);
        resp.setContentType("image/png");
        OutputStream stream=resp.getOutputStream();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos );
        byte[] imageInByte=baos.toByteArray();
        stream.write(imageInByte);
        stream.close();
    }
    
}
