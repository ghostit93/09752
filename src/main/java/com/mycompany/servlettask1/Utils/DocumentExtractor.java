/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1.Utils;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.fileupload.FileItem;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author Artur
 */
public class DocumentExtractor {
    
    public static String extractText(FileItem fileItem){
        try(InputStream inputStream=fileItem.getInputStream()){
            String contentType=fileItem.getContentType();
            switch(contentType){
                case "text/plain":
                    return new String(inputStream.readAllBytes());
                case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                    XWPFDocument document=new XWPFDocument(inputStream);
                    XWPFWordExtractor extractor=new XWPFWordExtractor(document);
                    return extractor.getText();
                case "application/msword":
                    WordExtractor wd = new WordExtractor(inputStream);
                    return wd.getText();
                default:
                    return null;
            }
        }catch(IOException ex){
            return null;
        }
    }
    
}
