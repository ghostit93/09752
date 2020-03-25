/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1;

import com.mycompany.servlettask1.DAO.*;
import com.mycompany.servlettask1.Entity.Order;

import com.mycompany.servlettask1.Entity.Product;
import com.mycompany.servlettask1.Entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Artur
 */
@WebServlet(value = "/OrderDetailsHandler")
public class OrderDetailsHandler extends HttpServlet{
    
    private UserService userService=new UserServiceImpl("jdbc:postgresql://localhost:5432/postgres2", "postgres", "root", "org.postgresql.Driver");
    private ProductService productService=new ProductServiceImpl("jdbc:postgresql://localhost:5432/postgres2", "postgres", "root", "org.postgresql.Driver");
    private OrderService orderService=new OrderServiceImpl("jdbc:postgresql://localhost:5432/postgres2", "postgres", "root", "org.postgresql.Driver",productService,userService);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view=req.getRequestDispatcher("/html/OrderDetails.jsp");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession=req.getSession();
        User user=(User)httpSession.getAttribute("user");
        if(user!=null){
            Cookie[] cookies=req.getCookies();
            List<Product> products = new ArrayList<>();
            int totalSum = 0;
            for (Cookie cookie : cookies) {
                if (cookie.getName().startsWith("catalog.product.id")) {
                    Long productId = Long.parseLong(cookie.getName().replace("catalog.product.id", ""));
                    Product product = productService.findById(productId);
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                    if (product != null) {
                        int counter = Integer.valueOf(cookie.getValue());
                        for (int i = 0; i < counter; i++) {
                            totalSum += product.getValue();
                            products.add(product);
                        }
                    }
                }
            }
            Order order=new Order(products, totalSum, user);
            orderService.save(order);
            RequestDispatcher view=req.getRequestDispatcher("/html/index.html");
            view.forward(req, resp);
        }
    }
    
}
