/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlettask1;

import com.ibm.icu.text.Transliterator;
import com.mycompany.servlettask1.DAO.DocumentService;
import com.mycompany.servlettask1.DAO.DocumentServiceImpl;
import com.mycompany.servlettask1.Entity.Document;
import com.mycompany.servlettask1.Utils.DocumentExtractor;
import com.sun.tools.javac.util.Constants;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Artur
 */
@WebServlet(value = "/doc")
public class DocumentServlet extends HttpServlet {

    private static final int MEMORY__THRESHOLD = 1024 * 1024 * 5;
    private static final int MAX__FILE__SIZE = 1024 * 1024 * 10;
    private static final int MAX__REQUEST__SIZE = 1024 * 1024 * 20;
    private static final String UPLOAD__DIRECTORY = "temp";
//    private Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");

    private DocumentService documentService;

    public DocumentServlet() {
        this.documentService = new DocumentServiceImpl("jdbc:postgresql://localhost:5432/postgres2","org.postgresql.Driver", "postgres", "root");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view=req.getRequestDispatcher("/html/FileUploadForm.html");
        resp.setContentType("text/html");
        view.forward(req, resp);
    }
    
    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            if (ServletFileUpload.isMultipartContent(req)) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(MEMORY__THRESHOLD);
                factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setFileSizeMax(MAX__FILE__SIZE);
                upload.setSizeMax(MAX__REQUEST__SIZE);
                String uploadPath = getServletContext().getRealPath("")
                        + File.separator + UPLOAD__DIRECTORY;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                List<FileItem> formItems = upload.parseRequest(req);
                if (formItems != null && formItems.size() > 0) {
                    for (FileItem item : formItems) {
                        if (!item.isFormField()) {
                            String fileName = item.getName();
                            String content=DocumentExtractor.extractText(item);
                            Document document=new Document(fileName, content);
//                            fileName=toLatinTrans.transform(fileName);
                            documentService.save(document);
                            req.setAttribute("fileName", fileName);
                            req.setAttribute("fileContent", content);
                            RequestDispatcher view=req.getRequestDispatcher("/html/FileContent.jsp");
                            resp.setContentType("text/html");
                            view.forward(req, resp);
                        }
                    }
                }
            }
        }catch(Exception exception){
            exception.printStackTrace();
            resp.sendError(resp.SC_BAD_REQUEST);
        }
    }

}
