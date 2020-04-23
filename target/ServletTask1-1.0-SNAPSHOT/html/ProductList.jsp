<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.mycompany.servlettask1.Entity.Product"%>
<%@page import="com.mycompany.servlettask1.DAO.ProductServiceImpl"%>
<%@page import="com.mycompany.servlettask1.DAO.ProductService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
  ProductService productService=new ProductServiceImpl("jdbc:postgresql://localhost:5432/postgres2", "postgres", "root", "org.postgresql.Driver");
  List<Product> products=productService.findAll();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Catalog</h1>
        <ul>
        <%
            for (Product product:products){
                out.println("<li>name:"+product.getName()+" price:"+product.getValue()+" <a href='addProduct?productId="+product.getId()+"'>|Add|</a></li>");
            }
        %>
        <ul/>
        <br/>
        <a href="OrderDetailsHandler">|To order|<a/>
        <br/>
        <a href="logout">Logout</a>
    </body>
</html>
