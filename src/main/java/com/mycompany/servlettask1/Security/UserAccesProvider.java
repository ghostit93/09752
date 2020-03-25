/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1.Security;

/**
 *
 * @author Artur
 */
public interface UserAccesProvider {
    
    public boolean hasAcces(String login,String password);
    
}
