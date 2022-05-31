<%@page import="sfg.assignment.dao.*"%>
<%@page import="sfg.assignment.model.*"%>
    <%@page import="java.text.DecimalFormat"%>
    <%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%
	DecimalFormat dcf = new DecimalFormat("#.##");
	request.setAttribute("dcf", dcf);
	UserAccount auth = (UserAccount) request.getSession().getAttribute("auth");
	
	if (auth == null) {
	    response.sendRedirect("views/login.jsp");
	}
	
	List<Order> orders = null;
	List<PointsTransaction> transactions=null;
	if (auth != null) {
	    request.setAttribute("auth", auth);
	    OrderDAO orderDao  = new OrderDAO();
		orders = orderDao.userOrders(auth.getId());
		PointsTransactionDAO trans=new PointsTransactionDAO();
		transactions=trans.getTransaction(auth.getId());
	}
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
                    <li class="active">My account </li>
                </ul>
            </div>
        </div>
    </div>
    <!-- my account wrapper start -->
    <div class="my-account-wrapper pt-100 pb-100">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <!-- My Account Page Start -->
                    <div class="myaccount-page-wrapper">
                        <!-- My Account Tab Menu Start -->
                        <div class="row">
                            <div class="col-lg-3 col-md-4">
                                <div class="myaccount-tab-menu nav" role="tablist"> 
                                    <a href="#trans" class="active" data-toggle="tab"><i class="fa fa-cart-arrow-down"></i> Transactions</a>
                                    <a href="#orders" data-toggle="tab"><i class="fa fa-user"></i> Order History</a>    
                                    <a href="#account-info" data-toggle="tab"><i class="fa fa-user"></i> Account Details</a>    
                                </div>
                            </div>
                            <!-- My Account Tab Menu End -->    
                            <!-- My Account Tab Content Start -->
                            <div class="col-lg-9 col-md-8">
                                <div class="tab-content" id="myaccountContent">
                                <div class="tab-pane fade show active" id="trans" role="tabpanel">
                                        <div class="myaccount-content">
                                            <h3>Loyalty Points History</h3>    
                                            <div class="myaccount-table table-responsive text-center">
                                                <table class="table table-bordered">
                                                    <thead class="thead-light">
                                                        <tr>
                                                            <th>Date</th>
                                                            <th>Transaction Type</th>
                                                            <th>Points</th>
                                                    </thead>    
                                                    <tbody>
                                                    <%
															if(transactions != null){
																for(PointsTransaction t:transactions){%>
                                                        <tr>
                                                            <td><%=t.getDate() %></td>
                                                            <td><%=t.getTransaction() %></td>
                                                            <td><%=dcf.format(t.getPoints())
                                                             %></td>
                                                        </tr>
                                                        	<%}
															}
															%>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Single Tab Content Start -->
                                    <div class="tab-pane fade show" id="orders" role="tabpanel">
                                        <div class="myaccount-content">
                                            <h3>Order History</h3>    
                                            <div class="myaccount-table table-responsive text-center">
                                                <table class="table table-bordered">
                                                    <thead class="thead-light">
                                                        <tr>
                                                            <th>Order Ref</th>
                                                            <th>Date</th>
                                                            <th>Payment Method</th>
                                                            <th>Quantity</th>
                                                            <th>Price/Points</th>
                                                            
                                                        </tr>
                                                    </thead>    
                                                    <tbody>
                                                    <%
															if(orders != null){
																for(Order o:orders){%>
                                                        <tr>
                                                            <td><%=o.getOrderId() %></td>
                                                            <td><%=o.getDate() %></td>
                                                            <td><%=o.getPaymentMethod() %></td>
                                                            <td><%=o.getQunatity() %></td>
                                                            <%if(o.getPaymentMethod().equals("Cash")){ %>
                                                            <td>R<%=dcf.format(o.getPrice()) %></td>
                                                            <%}else{%>
                                                            	<td>Pts<%=dcf.format(o.getPoints()) %></td>
                                                            	<%} %>
                                                        </tr>
                                                        	<%}
															}
															%>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Single Tab Content End -->    
                                    
                                    <!-- Single Tab Content End -->    
                                    <!-- Single Tab Content Start -->
                                    
                                    <!-- Single Tab Content End -->    
                                    <!-- Single Tab Content Start -->
                                    <div class="tab-pane fade" id="payment-method" role="tabpanel">
                                        <div class="myaccount-content">
                                            <h3>Payment Method</h3>
                                            <p class="saved-message">You Can't Saved Your Payment Method yet.</p>
                                        </div>
                                    </div>
                                    
                                    <!-- Single Tab Content End --> 
                                    <div class="tab-pane fade" id="account-info" role="tabpanel">
                                        <div class="myaccount-content">
                                            <h3>Account Details</h3>    
                                            <div class="account-details-form">
                                                
                                                    <div class="row">
                                                        <div class="col-lg-6">
                                                            <div class="single-input-item">
                                                                <label for="first-name" class="required">First Name</label>
                                                                <input type="text" id="first-name" value="<%=auth.getName() %>" readonly />
                                                            </div>
                                                        </div>   
                                                        <div class="col-lg-6">
                                                            <div class="single-input-item">
                                                                <label for="last-name" class="required" >Last Name</label>
                                                                <input type="text" id="last-name" value="<%=auth.getSurname()%>" readonly />
                                                            </div>
                                                        </div>
                                                    </div>    
                                                   
                                                    <div class="single-input-item">
                                                        <label for="email" class="required">Email Address</label>
                                                        <input type="email" id="email" value="<%=auth.getEmail()%>" readonly />
                                                    </div>   
                                                    <!-- <form action="#"> 
                                                    <fieldset>
                                                        <legend>Update Address</legend>
                                                        <div class="single-input-item">
                                                            <label for="current-pwd" class="required">Address</label>
                                                            <input type="text" name="address" id="current-pwd" />
                                                        </div>    
                                                    </fieldset>
                                                    <div class="single-input-item">
                                                        <button type="submit" class="check-btn sqr-btn ">Save Changes</button>
                                                    </div> -->
                                                </form>
                                            </div>
                                        </div>
                                    </div>   
                                    <!-- Single Tab Content Start -->
                                    
                            </div> <!-- My Account Tab Content End -->
                        </div>
                    </div> <!-- My Account Page End -->
                </div>
            </div>
        </div>
    </div>
    <%@include file="footer.jsp"%>
</body>
</html>