/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1;

import com.mycompany.servlettask1.DAO.UserService;
import com.mycompany.servlettask1.DAO.UserServiceImpl;
import com.mycompany.servlettask1.Entity.User;
import com.mycompany.servlettask1.Security.UserAccesProvider;
import com.mycompany.servlettask1.Security.UserJdbcAccesProvider;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Artur
 */
@WebServlet(value = "")
public class LoginServlet extends HttpServlet{

    private UserService UserService;
    private UserAccesProvider accesProvider;

    public LoginServlet() {
        UserService=new UserServiceImpl("jdbc:postgresql://localhost:5432/postgres2", "postgres", "root", "org.postgresql.Driver");
        accesProvider=new UserJdbcAccesProvider(UserService);
    }    
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view=req.getRequestDispatcher("/html/login.html");
        resp.setContentType("text/html");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password=req.getParameter("password");
        String username=req.getParameter("username");
        if (accesProvider.hasAcces(username, password))
        {
            RequestDispatcher view=req.getRequestDispatcher("/html/ProductList.jsp");
            HttpSession session=req.getSession();
            session.setAttribute("user", UserService.findByLogin(username).get(0));
            view.forward(req, resp);
        }
        else
            resp.setStatus(403);
    }
    
}
