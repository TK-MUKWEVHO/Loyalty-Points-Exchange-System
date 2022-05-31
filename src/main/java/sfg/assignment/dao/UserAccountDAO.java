package sfg.assignment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sfg.assignment.model.UserAccount;
import sfg.assignment.util.DBConnectionUtil;

public class UserAccountDAO {
	
	private Connection con;
	private String query;
    private PreparedStatement pst;
    private ResultSet rs;
    
    public boolean Login(String email,String password) {
    	boolean isLoggedIn=false;
    	
    	try {  		
            query = "SELECT * FROM tbl_account WHERE email=? and password=?";
            con=DBConnectionUtil.openConnection();
            pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);
            rs = pst.executeQuery();
            
            if(rs.next()){
            	isLoggedIn=true;
            }
            
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return isLoggedIn;
    	
    }
    
    
    public UserAccount userLogin(String email,String password) {
    	UserAccount user=null;
    	
    	try {  		
            query = "SELECT * FROM tbl_account WHERE email=? and password=?";
            con=DBConnectionUtil.openConnection();
            pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);
            rs = pst.executeQuery();
            
            if(rs.next()){
            	user= new UserAccount();
            	user.setId(rs.getInt("id"));
            	user.setName(rs.getString("name"));
            	user.setSurname(rs.getString("surname"));
            	user.setEmail(rs.getString("email"));
            	
            }
            
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return user;
    	
    }
    
    public boolean registerUser(UserAccount u) {
    	boolean registered=false;
    	
    	try{
    		String query="INSERT INTO tbl_account (name,surname,email,password) values(?,?,?,?)";
    		con=DBConnectionUtil.openConnection();
    		pst=con.prepareStatement(query);
    		pst.setString(1, u.getName());
    		pst.setString(2, u.getSurname());
    		pst.setString(3, u.getEmail());
    		pst.setString(4, u.getPassword());
    		pst.executeUpdate();
    		registered=true;
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return registered;
    }
    
    
    public boolean userExist(String email) {
    	boolean userExist=false;
    	
    	try {  		
            query = "SELECT * FROM tbl_account WHERE email=?";
            con=DBConnectionUtil.openConnection();
            pst = con.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();
            
            if(rs.next()){
            	userExist=true;	
            }
            
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return userExist;
    	
    }
    
    public UserAccount getUser(String email) {
    	UserAccount u=null;
    	
    	try {  		
            query = "SELECT * FROM tbl_account WHERE email=?";
            con=DBConnectionUtil.openConnection();
            pst = con.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();
            
            if(rs.next()){
            	u= new UserAccount();
            	u.setId(rs.getInt("id"));
            	u.setName(rs.getString("name"));
            	u.setSurname(rs.getString("surname"));
            	u.setEmail(rs.getString("email"));
            }
            
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return u;
    	
    }
    
    public String getPassword(String email) {
    	String password=null;
    	
    	try {  		
            query = "SELECT password FROM tbl_account WHERE email=?";
            con=DBConnectionUtil.openConnection();
            pst = con.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();
            
            if(rs.next()){
            	password=rs.getString("password");
            }
            
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return password;
    }
    
    
    
}
