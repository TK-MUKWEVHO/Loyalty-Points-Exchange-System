package sfg.assignment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import sfg.assignment.model.LoyaltyPoints;
import sfg.assignment.util.DBConnectionUtil;

public class LoyaltyPointsDAO {
	
	private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    DecimalFormat dcf = new DecimalFormat("#.##");
    
    public boolean addCustomer(LoyaltyPoints p) {
    	boolean customerAdded=false;
    	
    	try {
    		String query="INSERT INTO tbl_loyaltypoints (customerID,points) VALUES(?,?)";
    		con=DBConnectionUtil.openConnection();
    		pst=con.prepareStatement(query);
    		pst.setInt(1,p.getCustomerID());
    		pst.setDouble(2, p.getPoints());
    		pst.executeUpdate();
    		customerAdded=true;
    	}catch(SQLException e) {e.printStackTrace();}
    	
    	return customerAdded;
    }
    
    
    public double getCustomerPoints(int custID) {
       double custPoints=0;
       try {       
         String sql="SELECT points FROM tbl_loyaltypoints where customerID=?";
         con=DBConnectionUtil.openConnection();
         pst = con.prepareStatement(sql);
         pst.setInt(1, custID);
         rs = pst.executeQuery();
         
         while(rs.next()) {
        	 custPoints=rs.getDouble("points");
         }

       } catch (SQLException e) {
           e.printStackTrace();
           System.out.println(e.getMessage());
       }
       return custPoints;
    }
    
    public void redeemLoyaltyPoints(int custID, double points) {
    	
    	try{
    		String sql="UPDATE tbl_loyaltypoints SET points=? WHERE customerID=? ";
    		con=DBConnectionUtil.openConnection();
    		pst = con.prepareStatement(sql);
            pst.setDouble(1, points);
            pst.setInt(2, custID);
            pst.executeUpdate();
            
    	}catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    
public void earnLoyaltyPoints(int custID, double pointsEarned) {
    	
    	try{
    		String sql="UPDATE tbl_loyaltypoints SET points=? WHERE customerID=? ";
    		con=DBConnectionUtil.openConnection();
    		pst = con.prepareStatement(sql);
            pst.setDouble(1, pointsEarned);
            pst.setInt(2, custID);
            pst.executeUpdate();
            
    	}catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }  

}
