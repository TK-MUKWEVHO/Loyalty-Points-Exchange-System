<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="sfg.assignment.model.*"%>
    
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
                    <li class="active">login  </li>
                </ul>
            </div>
        </div>
    </div> 
    <div class="login-register-area pt-100 pb-100">
        <div class="container">
            <div class="row">
                <div class="col-lg-7 col-md-12 ml-auto mr-auto">
                    <div class="login-register-wrapper">
                    <div class="login-register-tab-list nav">
                            <a class="active" data-toggle="tab" href="">
                                <h4> register </h4>
                            </a>
                        </div>       
                        <div class="tab-content">
                            <div id="lg1" class="tab-pane active">
                                <div class="login-form-container">
                                    <div class="login-register-form">
                                    <%ErrorMessage msg = (ErrorMessage) request.getSession().getAttribute("error");
                                    	if(msg!=null){%>
                                    	<div class="alert alert-<%=msg.getCategory()%>" role="alert">
										  		<%=msg.getMessage() %>
										</div>
                                    		<% 
                                    	}
                                    	request.getSession().removeAttribute("error");
                                    %>
                                        <form action="${pageContext.request.contextPath}/AddAccountController" method="post">
                                            <input type="text" name="firstname" placeholder="First Name" required>
                                            <input type="text" name="lastname" placeholder="Last Name" required>
                                            <input name="useremail" placeholder="Email" type="email" required>
                                            <input type="password" name="user-password" placeholder="Password" title="Minimumof 8 characters, include atleast uppercase,lower case,special character and a number" required>
                                            <div class="button-box">
                                                <button type="submit">Register</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
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