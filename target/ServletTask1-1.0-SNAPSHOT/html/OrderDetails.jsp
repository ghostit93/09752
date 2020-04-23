<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.mycompany.servlettask1.Entity.Product"%>
<%@page import="com.mycompany.servlettask1.DAO.ProductServiceImpl"%>
<%@page import="com.mycompany.servlettask1.DAO.ProductService"%>
<%
  ProductService productService=new ProductServiceImpl("jdbc:postgresql://localhost:5432/postgres2", "postgres", "root", "org.postgresql.Driver");
  Cookie[] cookies=request.getCookies();
  List<Cookie> productCookies=new ArrayList<>();
  List<Product> products=new ArrayList<>();
  int totalSum=0;
  for (Cookie cookie:cookies){
    if (cookie.getName().startsWith("catalog.product.id")){
        Long productId=Long.parseLong(cookie.getName().replace("catalog.product.id", ""));
        Product product=productService.findById(productId);
        if (product!=null){
            int counter=Integer.valueOf(cookie.getValue());
            for (int i=0;i<counter;i++){
                totalSum+=product.getValue();
                products.add(product);
            }
        }
    }
  }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order list:</title>
    </head>
    <body>
        <h1>Order list:</h1>
        <form action="OrderDetailsHandler" method="POST">
            <ul>
            <%
                for (Product product:products){
                    out.println("<li>name:"+product.getName()+" price:"+product.getValue()+" </li>");
                }
            %>
            <ul/>
            <hr/>
            <span>Total price:<%=totalSum%></span>
            <br/>
            <input type="submit"/>
        </form>
    </body>
</html>
