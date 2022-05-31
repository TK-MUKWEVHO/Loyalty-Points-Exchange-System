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
    UserAccount auth = (UserAccount) request.getSession().getAttribute("auth");
    if (auth != null) {
        request.setAttribute("auth", auth);
    }
    
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
                    <li class="active">Checkout </li>
                </ul>
            </div>
        </div>
    </div>
    <!-- compare main wrapper start -->
    <div class="checkout-main-area pt-70 pb-70">
        <div class="container">
        <%ErrorMessage msg = (ErrorMessage) request.getSession().getAttribute("error");
                                    	if(msg!=null){%>
                                    	<div class="alert alert-<%=msg.getCategory()%>" role="alert">
										  		<%=msg.getMessage() %>
										</div>
                                    		<% 
                                    	}
                                    	request.getSession().removeAttribute("error");
                              %>
            <div class="checkout-wrap pt-30">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="your-order-area">
                            <h3>Your order</h3>
                            <div class="your-order-wrap gray-bg-4">
                                <div class="your-order-info-wrap">
                                    <div class="your-order-info">
                                        <ul>
                                            <li>Product <span>Quantity</span></li>
                                        </ul>
                                    </div>
                                    <div class="your-order-middle">
                                    <ul>
                                    <%
											if (cart_list != null) {
												for (Cart c : cartProduct) {
											%>
                                        
                                            <li><%=c.getName() %>     <span><%=c.getQuantity() %></span></li>
                                        
                                         <%
											}}else {%>
										<p>The Cart is Empty</p>
										<%}%> 
										</ul>
                                    </div>
                                    <div class="your-order-info order-subtotal">
                                        <ul>
                                            <li>Total in Loyalty Points<span>Pts: ${(totalP>0)?dcf.format(totalP):0} </span></li>
                                        </ul>
                                    </div>
                                    
                                    <div class="your-order-info order-total">
                                        <ul>
                                            <li>Total in Cash <span>R ${(total>0)?dcf.format(total):0}</span></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="Place-order mt-40">
                                <a onclick="return confirm('Please press OK to confirm payment with Loyalty Points');" href="${pageContext.request.contextPath}/CheckoutController?action=pts&pts=${totalP}">PAY Order With Loyalty Points</a>
                            </div>
                            <div class="Place-order mt-40">
                                <a href="${pageContext.request.contextPath}/CheckoutController?action=cash&cash=${total}">PAY Order with Cash on Delivery</a>
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