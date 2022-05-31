<%@page import="sfg.assignment.model.*"%>
<%@page import="sfg.assignment.dao.LoyaltyPointsDAO"%>
<%@page import="java.text.DecimalFormat"%>

<%
DecimalFormat df = new DecimalFormat("#.##");
HttpSession httpSession=request.getSession();

UserAccount auth1 = (UserAccount) httpSession.getAttribute("auth");
if(auth1!=null){
	LoyaltyPointsDAO points= new LoyaltyPointsDAO();
	  double myPoints=points.getCustomerPoints(auth1.getId());
	  request.setAttribute("myPoints", df.format(myPoints));
}  
%>
<div class="wrapper">
	<header class="header-area sticky-bar">
		<div class="main-header-wrap">
			<div class="container">
				<div class="row">
					<div class="col-xl-2 col-lg-2">
						<div class="logo pt-40">
							<a href="../index.jsp"> <img src="assets/img/logo/logo3.jpg"
								alt="">
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
							
							<%if(auth1!=null){ %>
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
									onclick="window.location.href='cart-page.jsp'">
									<span class="icon-cart"> <i class="sli sli-bag"></i> <span
										class="count-style">${cart_list.size()}</span>
									</span> <span class="cart-price"> Cart </span>
								</button>

							</div>
							<div class="setting-wrap">
								<button class="setting-active">
									<i class="sli sli-user"></i> <span class="cart-price">
										Account </span>
								</button>
								<div class="setting-content">
									<ul>
										<li>
											<h4>Account</h4>
											<ul>
												<%if(auth1!=null){ %>
												<li><a href="my-account.jsp">My Account</a></li>
												<li><a
													href="${pageContext.request.contextPath}/LogoutController">Logout</a></li>
												<%}else{ %>
												<li><a href="login.jsp">Login</a></li>
												<li><a href="register.jsp">Create Account</a></li>
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
			<!-- main-search start -->

		</div>
	</header>
	<div class="modal fade" id="pts" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
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
					<button type="button" class="btn btn-outline-danger"
						onclick="window.location.href='friend.jsp'">Transfer
						Points To Friend</button>
					<button type="button" class="btn btn-outline-danger"
						onclick="window.location.href='bank.jsp'">Transfer
						Points To Bank</button>
				</div>
			</div>
		</div>
	</div>