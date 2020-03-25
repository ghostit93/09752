/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1.Security;

import com.mycompany.servlettask1.DAO.UserService;
import com.mycompany.servlettask1.Entity.User;
import java.util.List;
import java.util.function.Predicate;
import sun.security.util.Password;

/**
 *
 * @author Artur
 */
public class UserJdbcAccesProvider implements UserAccesProvider{

    private UserService userService;

    public UserJdbcAccesProvider(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public boolean hasAcces(String login, String password) {
        List<User> users=userService.findByLogin(login);
        for (User user:users)
            if (user.getPassword()!=null && password.equals(user.getPassword()))
                return true;
        return false;
    }
    
}
