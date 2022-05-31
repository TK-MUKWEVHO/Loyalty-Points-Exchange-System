<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="sfg.assignment.util.DBConnectionUtil"%>
<%@page import="sfg.assignment.dao.ProductDAO"%>
<%@page import="sfg.assignment.dao.LoyaltyPointsDAO"%>
<%@page import="sfg.assignment.model.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%
DecimalFormat dcf = new DecimalFormat("#.##");
UserAccount auth = (UserAccount) request.getSession().getAttribute("auth");
if (auth != null) {
    request.setAttribute("auth", auth);
    LoyaltyPointsDAO points= new LoyaltyPointsDAO();
	  double myPoints=points.getCustomerPoints(auth.getId());
	  request.setAttribute("myPoints", dcf.format(myPoints));
}
ProductDAO pd = new ProductDAO();
List<Product> products = pd.getProducts();
ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
if (cart_list != null) {
	request.setAttribute("cart_list", cart_list);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>Welcome Gadgets online Store</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Favicon -->
<link rel="shortcut icon" type="image/x-icon"
	href="views/assets/img/favicon.png">

<!-- CSS
	============================================ -->

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="views/assets/css/bootstrap.min.css">
<!-- Icon Font CSS -->
<link rel="stylesheet" href="views/assets/css/icons.min.css">
<!-- Plugins CSS -->
<link rel="stylesheet" href="views/assets/css/plugins.css">
<!-- Main Style CSS -->
<link rel="stylesheet" href="views/assets/css/style.css">
<!-- Modernizer JS -->
<script src="views/assets/js/vendor/modernizr-2.8.3.min.js"></script>

</head>
</head>
<body>
	<div class="wrapper">
		<header class="header-area sticky-bar">
			<div class="main-header-wrap">
				<div class="container">
					<div class="row">
						<div class="col-xl-2 col-lg-2">
							<div class="logo pt-40">
								<a href="index.jsp"> <img
									src="views/assets/img/logo/logo3.jpg" alt="">
								</a>
							</div>
						</div>
						<div class="col-xl-7 col-lg-7 ">
							<div class="main-menu">
								<nav>
									<ul>
										<li class="angle-shape"><a href="">GROUP S-AB</a>
											<ul class="submenu">
												<li><a href="">MUKWEVHO, TM (218206751)</a></li>
												<li><a href="">MATHEVANI, EM (218624383)</a></li>
												<li><a href="">MATHABATHE, MB (218638082)</a></li>
											</ul></li>
									</ul>
								</nav>
							</div>
						</div>
						<div class="col-xl-3 col-lg-3">
							<div class="header-right-wrap pt-40">
								<%if(auth!=null){ %>
								<div class="cart-wrap">
									<button class="icon-cart-active" type="button"
										data-toggle="modal" data-target="#pts">
										<span class="icon-cart"> <i class="sli sli-wallet"></i>
										</span> <span class="cart-price"> Pts: ${myPoints} </span>
									</button>
								</div>
								<%} %>
								<div class="cart-wrap">
									<button class="icon-cart-active"
										onclick="window.location.href='views/cart-page.jsp'">
										<span class="icon-cart"> <i class="sli sli-bag"></i> <span
											class="count-style">${cart_list.size()}</span>
										</span> <span class="cart-price"> Cart </span>
									</button>

								</div>
								<div class="setting-wrap">
									<button class="setting-active">
										<i class="sli sli-user"></i>
										</span> <span class="cart-price"> Account </span>
									</button>
									<div class="setting-content">
										<ul>
											<li>
												<h4>Account</h4>
												<ul>
													<%if(auth!=null){ %>
													<li><a href="views/my-account.jsp">My Account</a></li>
													<li><a
														href="${pageContext.request.contextPath}/LogoutController">Logout</a></li>
													<%}else{ %>
													<li><a href="views/login.jsp">Login</a></li>
													<li><a href="views/register.jsp">Create Account</a></li>
													<%} %>
												</ul>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</header>
		<div class="slider-area section-padding-1">
			<div class="slider-active owl-carousel nav-style-1">
				<div class="single-slider slider-height-1 bg-paleturquoise">
					<div class="container">
						<div class="row align-items-center">
							<div class="col-xl-6 col-lg-6 col-md-6 col-12 col-sm-6">
								<div class="slider-content slider-animated-1">
									<h1 class="animated">SFG117V Gadgets Store</h1>
									<p class="animated">The nation's favourite Gadgets store</p>
									<div class="slider-btn btn-hover"></div>
								</div>
							</div>
							<div class="col-xl-6 col-lg-6 col-md-6 col-12 col-sm-6">
								<div class="slider-single-img slider-animated-1">
									<img class="animated"
										src="views/assets/img/slider/slider-hm1-1.png" alt="">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="section-title text-center pb-40">
				<h2>All Products</h2>
				<p>In our store</p>
			</div>
			<div class="row">
				<%
			if (!products.isEmpty()) {
				for (Product p : products) {
			%>
				<div class="col-md-3 my-3">
					<div class="ht-products ">
						<div
							class="ht-product ht-product-action-on-hover ht-product-category-right-bottom mb-30">
							<div class="ht-product-inner">
								<div class="ht-product-image-wrap">

									<a href="views/product-details.jsp?id=<%=p.getId()%>"
										class="ht-product-image"> <img
										src="views/product-image/<%=p.getImage() %>"
										alt="Universal Product Style">
									</a>
									<div class="ht-product-action">
										<ul>
											<li><a href="add-to-cart?id=<%=p.getId()%>"><i
													class="sli sli-bag"></i><span
													class="ht-product-action-tooltip">Add to Cart</span></a></li>
										</ul>
									</div>
								</div>
								<div class="ht-product-content">
									<div class="ht-product-content-inner">
										<div class="ht-product-categories">
											<a href=""><%=p.getCategory() %></a>
										</div>
										<h4 class="ht-product-title">
											<a href="views/product-details.jsp?id=<%=p.getId()%>"><%=p.getName() %></a>
										</h4>
										<div class="ht-product-price">
											<span class="new">R<%=p.getPrice() %></span> <span
												class="old">Pts: <%=p.getPoints()%></span>
										</div>
										<div class="ht-product-ratting-wrap"></div>
									</div>

								</div>
							</div>
						</div>
					</div>
				</div>
				<%
			}
			} else {
				
				
			out.println("There is no proucts");
			}
			%>
			</div>
		</div>

		<div class="modal fade" id="pts" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-sm" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">Loyalty Points</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>You have Pts: ${myPoints} in your Loyalty Points Account</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-outline-danger" onclick="window.location.href='views/friend.jsp'">Transfer Points To Friend</button>
						<button type="button" class="btn btn-outline-danger" onclick="window.location.href='views/bank.jsp'">Transfer Points To Bank</button>
					</div>
				</div>
			</div>
		</div>
		<footer class="footer-area bg-paleturquoise">
			<div class="footer-bottom border-top-1 pt-20">
				<div class="container">
					<div class="row align-items-center">
						<div class="col-lg-4 col-md-3 col-12">
							<div class="copyright text-center pb-20">
								<p>Copyright © All Right Reserved</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</footer>
		<!-- jQuery JS -->
		<script src="views/assets/js/vendor/jquery-1.12.4.min.js"></script>
		<!-- Popper JS -->
		<script src="views/assets/js/popper.min.js"></script>
		<!-- Bootstrap JS -->
		<script src="views/assets/js/bootstrap.min.js"></script>
		<!-- Plugins JS -->
		<script src="views/assets/js/plugins.js"></script>
		<!-- Ajax Mail -->
		<script src="views/assets/js/ajax-mail.js"></script>
		<!-- Main JS -->
		<script src="views/assets/js/main.js"></script>
</body>
</html>