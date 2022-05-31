package sfg.assignment.model;

public class LoyaltyPoints {
	
	private int customerID;
	private double points;
	
	public LoyaltyPoints() {
		
	}
	
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "LoyaltyPoints [customerID=" + customerID + ", points=" + points + "]";
	}
	
	
	
}
