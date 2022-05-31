package sfg.assignment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sfg.assignment.model.Customer;
import sfg.assignment.util.DBConnectionUtil;

public class CustomerDAO {
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	public boolean addCustomerToCustomer(Customer c) {
		boolean custAdded = false;
		try {
			String query = "INSERT INTO tbl_customer (custID,name,surname,email,adress) VALUES(?,?,?,?,?)";
			con = DBConnectionUtil.openConnection();
			pst = con.prepareStatement(query);
			pst.setInt(1, c.getId());
			pst.setString(2, c.getName());
			pst.setString(3, c.getSurname());
			pst.setString(4, c.getEmail());
			pst.setString(5, c.getAddress());
			pst.executeUpdate();
			custAdded = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return custAdded;
	}

	public Customer getFriend(String email) {
		Customer friend=null;

		String query = "SELECT * from tbl_customer where email=?";
		try {
			con = DBConnectionUtil.openConnection();
			pst = con.prepareStatement(query);
			pst.setString(1, email);
			rs = pst.executeQuery();

			while (rs.next()) {
				friend=new Customer();
				friend.setId(rs.getInt("custID"));
				friend.setName(rs.getString("name"));
				friend.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friend;
	}
}
