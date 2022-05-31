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

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		try {
			String email = request.getParameter("user-email");
			String password = request.getParameter("user-password");
			
			UserAccountDAO account=new UserAccountDAO();
			
			
			boolean status=account.Login(email, password);
			if(status) {
				UserAccount user=account.userLogin(email, password);
				session.setAttribute("auth",user);
				response.sendRedirect("index.jsp");
			}else {
				ErrorMessage error=new ErrorMessage();
				error.setMessage("Email or password is incorrect");
				error.setCategory("danger");
				session.setAttribute("error", error);
				response.sendRedirect("views/login.jsp");
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
	}

}

