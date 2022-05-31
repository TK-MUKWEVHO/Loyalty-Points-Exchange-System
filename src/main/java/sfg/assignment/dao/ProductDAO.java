package sfg.assignment.dao;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import sfg.assignment.model.Cart;
import sfg.assignment.model.Product;
import sfg.assignment.util.DBConnectionUtil;


public class ProductDAO {
	
	private Connection con;
	private ResultSet rs;
	private PreparedStatement pst;
	Statement statement=null;

	
	public List<Product> getProducts() {
		List<Product> list=null;
		Product product=null;
		try {
			list=new ArrayList<Product>();
			String sql="SELECT * from tbl_product";
			con=DBConnectionUtil.openConnection();
			statement=con.createStatement();
			rs=statement.executeQuery(sql);
			while(rs.next()) {
				product=new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setPrice(rs.getDouble("price"));
				product.setPoints(rs.getDouble("points"));
				product.setImage(rs.getString("image"));
				list.add(product);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public double getTotalCartPoints(ArrayList<Cart> cartList) {
		String query;
        double sumPoints = 0;
        try {
            if (cartList.size() > 0) {
                for (Cart item : cartList) {
                    query = "select points from tbl_product where id=?";
                    con=DBConnectionUtil.openConnection();
                    pst = this.con.prepareStatement(query);
                    pst.setInt(1, item.getId());
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        sumPoints+=rs.getDouble("points")*item.getQuantity();
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return sumPoints;
    }
	
	public double getTotalCartPrice(ArrayList<Cart> cartList) {
		String query;
        double sum = 0;
        try {
            if (cartList.size() > 0) {
                for (Cart item : cartList) {
                    query = "select price from tbl_product where id=?";
                    con=DBConnectionUtil.openConnection();
                    pst = this.con.prepareStatement(query);
                    pst.setInt(1, item.getId());
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        sum+=rs.getDouble("price")*item.getQuantity();
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return sum;
    }
	
	
	public List<Cart> getCartProducts(ArrayList<Cart> cartList) {
        List<Cart> cart = new ArrayList<>();
        String query;
        try {
            if (cartList.size() > 0) {
                for (Cart item : cartList) {
                    query = "select * from tbl_product where id=?";
                    con=DBConnectionUtil.openConnection();
                    pst = this.con.prepareStatement(query);
                    pst.setInt(1, item.getId());
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        Cart row = new Cart();
                        row.setId(rs.getInt("id"));
                        row.setName(rs.getString("name"));
                        row.setCategory(rs.getString("category"));
                        row.setPrice(rs.getDouble("price")*item.getQuantity());
                        row.setPrice(rs.getDouble("points")*item.getQuantity());
                        row.setQuantity(item.getQuantity());
                        cart.add(row);
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return cart;
    }
	
	public Product getSingleProduct(int id) {
		 Product row = null;
	        try {
	            String query = "select * from tbl_product where id=? ";
	            con=DBConnectionUtil.openConnection();
	            pst = this.con.prepareStatement(query);
	            pst.setInt(1, id);
	            ResultSet rs = pst.executeQuery();

	            while (rs.next()) {
	            	row = new Product();
	                row.setId(rs.getInt("id"));
	                row.setName(rs.getString("name"));
	                row.setCategory(rs.getString("category"));
	                row.setPrice(rs.getDouble("price"));
	                row.setPoints(rs.getDouble("points"));
	                row.setImage(rs.getString("image"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	        }

	        return row;
	    }
	
}
