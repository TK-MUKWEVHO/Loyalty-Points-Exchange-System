package sfg.assignment.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sfg.assignment.dao.CustomerDAO;
import sfg.assignment.dao.LoyaltyPointsDAO;
import sfg.assignment.dao.PointsTransactionDAO;
import sfg.assignment.model.Customer;
import sfg.assignment.model.ErrorMessage;
import sfg.assignment.model.PointsTransaction;
import sfg.assignment.model.UserAccount;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransferPointsToFriendController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserAccount auth = (UserAccount) request.getSession().getAttribute("auth");
		String email = request.getParameter("useremail");
		double points = Double.parseDouble(request.getParameter("points"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			// RFC 5322 email validation
			String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
			boolean validEmail = email.matches(regex);

			LoyaltyPointsDAO send = new LoyaltyPointsDAO();
			CustomerDAO f = new CustomerDAO();

			if (validEmail) {
				// getting friend details
				Customer friend = f.getFriend(email);
				if (friend != null) {
					boolean eqaul = auth.getEmail().equals(friend.getEmail());
					if (eqaul) {
						ErrorMessage error = new ErrorMessage();
						error.setMessage("You can not transfer points to yourself");
						error.setCategory("danger");
						session.setAttribute("error", error);
						response.sendRedirect("views/friend.jsp");
					} else {
						double myPoints = send.getCustomerPoints(auth.getId());
						if (myPoints >= points) {
							double pointsD = myPoints - points;
							// adding to transaction
							PointsTransaction trans = new PointsTransaction();
							trans.setCustID(auth.getId());
							trans.setDate(formatter.format(date));
							trans.setPoints(points);
							trans.setTransaction("Transfer to Friend(" + friend.getName() + ")");
							PointsTransactionDAO p = new PointsTransactionDAO();
							p.addTransaction(trans);

							// updating friend points
							double newPoints = send.getCustomerPoints(friend.getId()) + points;
							send.earnLoyaltyPoints(friend.getId(), newPoints);

							// adding to transaction
							PointsTransaction receive = new PointsTransaction();
							receive.setCustID(friend.getId());
							receive.setDate(formatter.format(date));
							receive.setPoints(points);
							receive.setTransaction("Recieved points from (" + auth.getName() + ")");
							PointsTransactionDAO r = new PointsTransactionDAO();
							r.addTransaction(receive);

							send.redeemLoyaltyPoints(auth.getId(), pointsD);

							ErrorMessage error = new ErrorMessage();
							error.setMessage("You successfully transfered " + points + " to " + friend.getName());
							error.setCategory("success");
							session.setAttribute("error", error);
							response.sendRedirect("views/friend.jsp");
						} else {
							ErrorMessage error = new ErrorMessage();
							error.setMessage("Points you want to transfer exceeds points you have");
							error.setCategory("danger");
							session.setAttribute("error", error);
							response.sendRedirect("views/friend.jsp");
						}
					}
				} else {
					ErrorMessage error = new ErrorMessage();
					error.setMessage("Friend not found");
					error.setCategory("danger");
					session.setAttribute("error", error);
					response.sendRedirect("views/friend.jsp");
				}
			} else {
				ErrorMessage error = new ErrorMessage();
				error.setMessage("Email you entered is not correctly formatted");
				error.setCategory("danger");
				session.setAttribute("error", error);
				response.sendRedirect("views/friend.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
