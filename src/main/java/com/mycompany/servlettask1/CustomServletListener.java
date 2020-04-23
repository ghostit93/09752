/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Artur
 */
@WebListener
public class CustomServletListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        System.out.println("Session was created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        System.out.println("Session was destroyed");
    }
    
}
