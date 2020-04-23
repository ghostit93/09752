/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1.DAO;

import com.mycompany.servlettask1.Entity.Document;

/**
 *
 * @author Artur
 */
public interface DocumentService {
    
    public Document save(Document document);
    public Document findById(long id);
    
}
