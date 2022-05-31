package sfg.assignment.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sfg.assignment.dao.CustomerDAO;
import sfg.assignment.dao.LoyaltyPointsDAO;
import sfg.assignment.dao.UserAccountDAO;
import sfg.assignment.model.Customer;
import sfg.assignment.model.ErrorMessage;
import sfg.assignment.model.LoyaltyPoints;
import sfg.assignment.model.UserAccount;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class AddAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String firstname=request.getParameter("firstname");
		String lastname=request.getParameter("lastname");
		String email = request.getParameter("useremail");
		String password = request.getParameter("user-password");
		
		UserAccountDAO u = new UserAccountDAO();
		try (PrintWriter out = response.getWriter()){
			//validate email
			String regex="^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"; 
			//validate password
			String regexP = "^(?=.*[0-9])"
                    + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[@#$%^&+=])"
                    + "(?=\\S+$).{8,20}$";
			
			Pattern p = Pattern.compile(regexP);
			Matcher m = p.matcher(password);
			boolean validPassword=m.matches();
			boolean validEmail=email.matches(regex);
			
			if(validEmail) {
				if(validPassword) {
			UserAccount newUser=new UserAccount();
			newUser.setName(firstname);
			newUser.setSurname(lastname);
			newUser.setEmail(email);
			newUser.setPassword(password);
			
			boolean userExist=u.userExist(email);
			
			if(userExist) {
				ErrorMessage error=new ErrorMessage();
				error.setMessage("The Email entered is already exist");
				error.setCategory("danger");
				session.setAttribute("error", error);
				response.sendRedirect("views/register.jsp");
			}else {
			boolean results=u.registerUser(newUser);
			if(results) {
				UserAccount user=u.userLogin(email, password);
				
				LoyaltyPoints newAcc=new LoyaltyPoints();
				newAcc.setCustomerID(user.getId());
				newAcc.setPoints(0.0);
				LoyaltyPointsDAO addLAcc=new LoyaltyPointsDAO();
				
				addLAcc.addCustomer(newAcc);//add customer to loyalty points
				
				Customer addC=new Customer();
				addC.setId(user.getId());
				addC.setName(user.getName());
				addC.setSurname(user.getSurname());
				addC.setEmail(user.getEmail());
				addC.setAddress("");
				CustomerDAO addCust=new CustomerDAO();
				addCust.addCustomerToCustomer(addC);//add customer to customer
				ErrorMessage error=new ErrorMessage();
				error.setMessage("Succefully Registered Account please Login");
				error.setCategory("success");
				session.setAttribute("error", error);
				response.sendRedirect("views/login.jsp");

			}else {
				ErrorMessage error=new ErrorMessage();
				error.setMessage("Account failed");
				error.setCategory("danger");
				session.setAttribute("error", error);
				response.sendRedirect("views/register.jsp");
			}
		 }
		}else {
			ErrorMessage error=new ErrorMessage();
			error.setMessage("Password does not meet requirements");
			error.setCategory("danger");
			session.setAttribute("error", error);
			response.sendRedirect("views/register.jsp");
		}
		}else {
			ErrorMessage error=new ErrorMessage();
			error.setMessage("Email you entered is not correctly formatted");
			error.setCategory("danger");
			HttpSession s=request.getSession();
			s.setAttribute("error", error);
			response.sendRedirect("views/register.jsp");
			
		}
		}catch(Exception e) {e.printStackTrace();}
	}

}
