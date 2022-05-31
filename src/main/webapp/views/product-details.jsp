<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="sfg.assignment.util.DBConnectionUtil"%>
<%@page import="sfg.assignment.dao.ProductDAO"%>
<%@page import="sfg.assignment.model.*"%>
<%@page import="java.util.*"%>

<%
	int id = Integer.parseInt(request.getParameter("id"));
	ProductDAO singleProduct=new ProductDAO();
	Product prod=singleProduct.getSingleProduct(id);
ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
if (cart_list != null) {
	request.setAttribute("cart_list", cart_list);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <%@include file="head.jsp"%>
</head>
<body>
 <%@include file="navbar.jsp"%>
<div class="breadcrumb-area pt-35 pb-35 bg-gray">
        <div class="container">
            <div class="breadcrumb-content text-center">
                <ul>
                    <li>
                        <a href="../index.jsp">Home</a>
                    </li>
                    <li class="active">Product Details </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="product-details-area pt-100 pb-95">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-6">
                    <div class="product-details-img">
                        <div class="zoompro-border zoompro-span">
                                 <img class="zoompro" src="product-image/<%=prod.getImage() %>"/>     
                            <div class="product-video">
                               
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6">
                    <div class="product-details-content ml-30">
                        <h2><%=prod.getName() %></h2>
                        <div class="product-details-price">
                            <span>R<%=prod.getPrice() %></span>
			    			<span class="old">Pts: <%=prod.getPoints() %></span>
                         
                        </div>
                        
                        
            
                        <div class="pro-details-size-color">
                            <div class="pro-details-color-wrap">
                                <span><%=prod.getCategory() %></span>
                                
                            </div>
                            
                        </div>
                        <div class="pro-details-quality">
                            
                            <div class="pro-details-cart btn-hover">
                                <a href="../add-to-cart?id=<%=prod.getId()%>">Add To Cart</a>
                            </div>
                        </div>
                       
                    </div>
                </div>
            </div>
        </div>
    </div>
     <%@include file="footer.jsp"%>
</body>
</html>