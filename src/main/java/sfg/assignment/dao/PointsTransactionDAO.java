package sfg.assignment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sfg.assignment.model.PointsTransaction;
import sfg.assignment.util.DBConnectionUtil;

public class PointsTransactionDAO {
	private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    
	public boolean addTransaction(PointsTransaction p) {
    	boolean transactionAdded=false;
    	
    	try {
    		String query="INSERT INTO tbl_transaction (custID,date,points,transaction) VALUES(?,?,?,?)";
    		con=DBConnectionUtil.openConnection();
    		pst=con.prepareStatement(query);
    		pst.setInt(1,p.getCustID());
    		pst.setString(2, p.getDate());
    		pst.setDouble(3, p.getPoints());
    		pst.setString(4, p.getTransaction());
    		pst.executeUpdate();
    		transactionAdded=true;
    	}catch(SQLException e) {e.printStackTrace();}
    	return transactionAdded;
    }
	
	public List<PointsTransaction> getTransaction(int id) {
		List<PointsTransaction> list = new ArrayList<>();
    	
    	try {
    		String query="SELECT * FROM tbl_transaction where custID=?";
    		con=DBConnectionUtil.openConnection();
    		pst=con.prepareStatement(query);
    		pst.setInt(1,id);
    		rs = pst.executeQuery();
    		while(rs.next()) {
    			PointsTransaction pt =new PointsTransaction();
    			pt.setDate(rs.getString("date"));
    			pt.setPoints(rs.getDouble("points"));
    			pt.setTransaction(rs.getString("transaction"));
    			list.add(pt);
    		}
    	}catch(SQLException e) {e.printStackTrace();}
    	return list;
    }
}
