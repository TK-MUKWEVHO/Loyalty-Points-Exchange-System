package sfg.assignment.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sfg.assignment.dao.LoyaltyPointsDAO;
import sfg.assignment.dao.PointsTransactionDAO;
import sfg.assignment.model.ErrorMessage;
import sfg.assignment.model.PointsTransaction;
import sfg.assignment.model.UserAccount;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransferPointsToBankController extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		UserAccount auth = (UserAccount) request.getSession().getAttribute("auth");
		
		double points=Double.parseDouble(request.getParameter("points"));
		String bankName=request.getParameter("bankname");
		int accountnumber=Integer.parseInt(request.getParameter("accnumber"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
		try {
			LoyaltyPointsDAO send = new LoyaltyPointsDAO();
			
			double myPoints =send.getCustomerPoints(auth.getId());
			if(myPoints>=points) {
				double pointsD=myPoints-points;
				
				//add transaction
				PointsTransaction trans=new PointsTransaction();
				trans.setCustID(auth.getId());
				trans.setDate(formatter.format(date));
				trans.setPoints(points);
				trans.setTransaction("Transfered to ("+bankName+")");
				PointsTransactionDAO p=new PointsTransactionDAO();
				p.addTransaction(trans);
				
				send.redeemLoyaltyPoints(auth.getId(), pointsD);
				
				String info="You Successfully transfered "+points+" points to "+bankName+" bank("+accountnumber+") points transfered equal R"+points/2;
				ErrorMessage error=new ErrorMessage();
				error.setMessage(info);
				error.setCategory("success");
				session.setAttribute("error", error);
				response.sendRedirect("views/bank.jsp");
			}else {
				ErrorMessage error = new ErrorMessage();
				error.setMessage("Points you want to transfer exceeds points you have");
				error.setCategory("danger");
				session.setAttribute("error", error);
				response.sendRedirect("views/bank.jsp");
			}
		}catch(Exception e) {e.printStackTrace();}
		
	}

}
