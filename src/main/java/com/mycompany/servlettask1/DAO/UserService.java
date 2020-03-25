/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1.DAO;

import com.mycompany.servlettask1.Entity.User;
import java.util.List;

/**
 *
 * @author Artur
 */
public interface UserService {
    
    void delete(long id);
    User save(User user);
    User findById(long id);
    public List<User> findByLogin(String login);
    
}
