package sfg.assignment.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sfg.assignment.dao.ProductDAO;
import sfg.assignment.model.Product;

import java.io.IOException;


public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		
		int id = Integer.parseInt(request.getParameter("id"));
		ProductDAO singleProduct=new ProductDAO();
		Product prod=singleProduct.getSingleProduct(id);
		
		session.setAttribute("singleProd",prod);
		response.sendRedirect("/views/product-details.jsp");
		
	}
}
