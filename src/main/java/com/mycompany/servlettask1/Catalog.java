/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Artur
 */
@WebServlet(value = "/addProduct")
public class Catalog extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId=req.getParameter("productId");
        if (productId!=null && isLong(productId)){
            addProductToCookie(req, resp, productId);
        }
        RequestDispatcher view=req.getRequestDispatcher("/html/ProductList.jsp");
        view.forward(req, resp);
    }
    
    public void addProductToCookie(HttpServletRequest req,HttpServletResponse res,String id){
        Cookie[] cookies=req.getCookies();
        boolean find=false;
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("catalog.product.id"+id)){
                find=true;
                long val=Long.parseLong(cookie.getValue());
                val++;
                cookie.setValue(String.valueOf(val));
            }
        }
        if (!find){
            res.addCookie(new Cookie("catalog.product.id"+id, "1"));
        }
    }
    
    public static boolean isLong(String num){
        try{
            Long.parseLong(num);
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    
}
