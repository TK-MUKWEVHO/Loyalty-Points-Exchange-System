package sfg.assignment.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import sfg.assignment.model.Order;
import sfg.assignment.model.Product;
import sfg.assignment.util.DBConnectionUtil;

public class OrderDAO {
	private Connection con;

	private String query;
    private PreparedStatement pst;
    private ResultSet rs;
	
	public boolean insertOrder(Order model) {
        boolean result = false;
        try {
            query = "insert into tbl_order (p_id, u_id, o_quantity,o_pay_method, o_date) values(?,?,?,?,?)";
            con=DBConnectionUtil.openConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, model.getId());
            pst.setInt(2, model.getUid());
            pst.setInt(3, model.getQunatity());
            pst.setString(4, model.getPaymentMethod());
            pst.setString(5, model.getDate());
            pst.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
	

    public List<Order> userOrders(int id) {
        List<Order> list = new ArrayList<>();
        try {
            query = "select * from tbl_order where u_id=? order by tbl_order.o_id desc";
            con=DBConnectionUtil.openConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                ProductDAO productDao = new ProductDAO();
                int pId = rs.getInt("p_id");
                Product product = productDao.getSingleProduct(pId);
                order.setOrderId(rs.getInt("o_id"));
                order.setId(pId);
                order.setName(product.getName());
                order.setCategory(product.getCategory());
                order.setPrice(product.getPrice()*rs.getInt("o_quantity"));
                order.setPoints(product.getPoints()*rs.getInt("o_quantity"));
                order.setQunatity(rs.getInt("o_quantity"));
                order.setPaymentMethod(rs.getString("o_pay_method"));
                order.setDate(rs.getString("o_date"));
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void cancelOrder(int id) {
        //boolean result = false;
        try {
            query = "delete from tbl_order where o_id=?";
            con=DBConnectionUtil.openConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.execute();
            //result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
        //return result;
    }

}
