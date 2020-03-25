/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1.DAO;

import com.mycompany.servlettask1.Entity.Order;
import java.util.List;

/**
 *
 * @author Artur
 */
public interface OrderService {
    
    public Order save(Order order);
    public List<Order> findAll();
    
}
