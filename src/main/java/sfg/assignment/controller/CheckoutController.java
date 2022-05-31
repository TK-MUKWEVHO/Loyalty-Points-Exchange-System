package sfg.assignment.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sfg.assignment.dao.LoyaltyPointsDAO;
import sfg.assignment.dao.OrderDAO;
import sfg.assignment.dao.PointsTransactionDAO;
import sfg.assignment.model.Cart;
import sfg.assignment.model.ErrorMessage;
import sfg.assignment.model.Order;
import sfg.assignment.model.PointsTransaction;
import sfg.assignment.model.UserAccount;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DecimalFormat dcf = new DecimalFormat("#.##");
		
		HttpSession session=request.getSession();
		try(PrintWriter out = response.getWriter()){
			LoyaltyPointsDAO pts=new LoyaltyPointsDAO();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
			ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			UserAccount auth = (UserAccount) request.getSession().getAttribute("auth");
			String priceP = request.getParameter("pts");
			String priceC =request.getParameter("cash");
			String action = request.getParameter("action");
			
			//getting user points
			double custPoints= pts.getCustomerPoints(auth.getId());
			switch(action) {
			//buying with points
			case "pts":
				if(cart_list != null && auth!=null) {
					
					String paymentMethod="Loyalty Points";
					if(custPoints>=Double.parseDouble(priceP)) {
						double pointsS =custPoints-Double.parseDouble(priceP);
						PointsTransaction trans=new PointsTransaction();
						trans.setCustID(auth.getId());
						trans.setDate(formatter.format(date));
						trans.setPoints(pointsS);
						trans.setTransaction("Payed order");
						PointsTransactionDAO p=new PointsTransactionDAO();
						p.addTransaction(trans);
						pts.redeemLoyaltyPoints(auth.getId(), pointsS);
						for(Cart c:cart_list) {
							Order order = new Order();
							order.setId(c.getId());
							order.setUid(auth.getId());
							order.setQunatity(c.getQuantity());
							order.setDate(formatter.format(date));
							order.setPaymentMethod(paymentMethod);
							OrderDAO oDao = new OrderDAO();
							boolean result = oDao.insertOrder(order);
							if(!result) break;
						}
						cart_list.clear();
						
						ErrorMessage error=new ErrorMessage();
						error.setMessage("You Successfull reedem your Loyalty Points by paying order");
						error.setCategory("success");
						session.setAttribute("error", error);
						response.sendRedirect("views/successful.jsp");
					}else {
						ErrorMessage error=new ErrorMessage();
						error.setMessage("You dont have enough points");
						error.setCategory("danger");
						session.setAttribute("error", error);
						response.sendRedirect("views/checkout.jsp");
					}
				}else {
					if(auth==null) {
						response.sendRedirect("login-register.jsp");
					}
					response.sendRedirect("cart-page.jsp");
				}
				break;
				//buying with cash
			case "cash":
				//adding to order table 
				if(cart_list != null && auth!=null) {
					for(Cart c:cart_list) {
						Order order = new Order();
						order.setId(c.getId());
						order.setUid(auth.getId());
						order.setQunatity(c.getQuantity());
						order.setDate(formatter.format(date));
						order.setPaymentMethod("Cash");
						OrderDAO oDao = new OrderDAO();
						boolean result = oDao.insertOrder(order);
						if(!result) {break;}
					}
						//earn points
						double pointsEarned=custPoints+(Double.parseDouble(priceC)/1000)*25;
						//update loyalty points table
						pts.earnLoyaltyPoints(auth.getId(), pointsEarned);
					
						cart_list.clear();
						//adding transaction
						PointsTransaction earn=new PointsTransaction();
						earn.setCustID(auth.getId());
						earn.setDate(formatter.format(date));
						earn.setPoints((Double.parseDouble(priceC)/1000)*25);
						earn.setTransaction("Earned from Buying");
						PointsTransactionDAO p=new PointsTransactionDAO();
						p.addTransaction(earn);
						
						//message
						ErrorMessage error=new ErrorMessage();
						error.setMessage("Oder Successfull and you have earned "+dcf.format((Double.parseDouble(priceC)/1000)*25)+" points");
						error.setCategory("success");
						session.setAttribute("error", error);
						response.sendRedirect("views/successful.jsp");
					}else {
					if(auth==null) {
						response.sendRedirect("views/login.jsp");
					}
					response.sendRedirect("views/cart-page.jsp");
				}
				break;	
			default:
				System.out.println("its Bad");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
