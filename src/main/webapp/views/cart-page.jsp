<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="sfg.assignment.util.DBConnectionUtil"%>
<%@page import="sfg.assignment.dao.ProductDAO"%>
<%@page import="sfg.assignment.model.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DecimalFormat"%>
    
    <%
    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
    
    HttpSession httpSession1=request.getSession();
    UserAccount auth2 = (UserAccount) httpSession1.getAttribute("auth");
    
    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    List<Cart> cartProduct = null;
    if (cart_list != null) {
    	
    	ProductDAO pDao = new ProductDAO();
    	cartProduct = pDao.getCartProducts(cart_list);
    	double total = pDao.getTotalCartPrice(cart_list);
    	double totalP=pDao.getTotalCartPoints(cart_list);
    	request.setAttribute("totalP", totalP);
    	request.setAttribute("total", total);
    	request.setAttribute("cart_list", cart_list);
    }
    
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%@include file="head.jsp"%>
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
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
                    <li class="active">Cart page </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="cart-main-area pt-95 pb-100">
        <div class="container">
            <h3 class="cart-page-title">Your cart items</h3>
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                    <form action="#">
                        <div class="table-content table-responsive cart-table-content">
                        <%if (cart_list != null) { %>
                            <table>
                                <thead>
                                    <tr>
                                        
                                        <th>Product Name</th>
                                        <th>Price</th>
                                 		 <th>Quantity</th>
                                         <th>action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <%
											
												for (Cart c : cartProduct) {
											%>
                                    <tr>
                                        <td class="product-name"><a href="#"><%=c.getName()%></a></td>
                                        <td class="product-price-cart"><span class="amount">R<%= dcf.format(c.getPrice())%></span></td>
                                        <td class="product-quantity">
                                            <a class="btn bnt-sm btn-incre" href="${pageContext.request.contextPath}/IncrementDecrementController?action=inc&id=<%=c.getId()%>"><i class="fas fa-plus-square"></i></a> 
														<input type="text" name="quantity" value="<%=c.getQuantity()%>" size="1" readonly>
														<a class="btn btn-sm btn-decre" href="${pageContext.request.contextPath}/IncrementDecrementController?action=dec&id=<%=c.getId()%>"><i class="fas fa-minus-square"></i></a>
                                        </td>
                                        <td class="product-remove">
                                           
                                            <a href="${pageContext.request.contextPath}/RemoveFromCartController?id=<%=c.getId() %>"><i class="sli sli-close"></i></a>
                                       </td>
                                    </tr>  
                                    <%
									}}else {%>
										<p>The Cart is Empty</p>
										<%}%>     
                                </tbody>
                            </table>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="cart-shiping-update-wrapper">
                                    <div class="cart-shiping-update">
                                        <a href="../index.jsp">Continue Shopping</a>
                                    </div>
                              
                                </div>
                            </div>
                        </div>
                    </form>
                   <%  if (cart_list != null){%>
                    <div class="row">
                        <div class="col-lg-4">
                            <div class="grand-totall">
                                <div class="title-wrap">
                                    <h4 class="cart-bottom-title section-bg-gary-cart">Cart Total</h4>
                                </div>
                                <h5>Total in Cash <span>R ${(total>0)?dcf.format(total):0}</span></h5>
                                <h5>Total in Points <span>Pts ${(totalP>0)?dcf.format(totalP):0}</span></h5>
                                <%if(auth2!=null){%>
                                	<a href="checkout.jsp">Proceed to Checkout</a>
                                <%}else{ %>
                                 	<a href="login.jsp?from=cart">Login/Register Account to Checkout</a>
                                 <%} %>
                            </div>
                        </div>
                    </div>
                    <%} %>
                </div>
            </div>
        </div>
    </div>
<%@include file="footer.jsp"%>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>