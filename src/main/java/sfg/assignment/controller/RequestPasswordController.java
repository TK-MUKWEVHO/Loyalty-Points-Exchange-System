package sfg.assignment.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sfg.assignment.dao.UserAccountDAO;
import sfg.assignment.model.ErrorMessage;
import sfg.assignment.model.UserAccount;

import java.io.IOException;


public class RequestPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String email=request.getParameter("useremail");
		
		UserAccountDAO getuser=new UserAccountDAO();
		 UserAccount u=getuser.getUser(email);
		 
		 if(u!=null) {
			 String password=getuser.getPassword(email);
			 	ErrorMessage error=new ErrorMessage();
				error.setMessage("The Password has been sent to your email");
				error.setCategory("success");
				session.setAttribute("error", error);
				response.sendRedirect("views/request.jsp");
		 }else {
			 	ErrorMessage error=new ErrorMessage();
				error.setMessage("The user does not exist");
				error.setCategory("error");
				session.setAttribute("error", error);
				response.sendRedirect("views/request.jsp");
		 }
	}

}
