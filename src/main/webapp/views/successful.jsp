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
    <div class="login-register-area pt-100 pb-100">
        <div class="container">
            <div class="row">
                <div class="col-lg-7 col-md-12 ml-auto mr-auto">
                    <div class="login-register-wrapper">
                    <div class="login-register-tab-list nav">
                            <a class="active" data-toggle="tab" href="">
                                <h4> Order Status </h4>
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
                                    <div class="button-box">
                                    <div class="login-toggle-btn">
                                                    <a href="../index.jsp">Return Home</a>
                                            </div>
                                            </div>
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