package sfg.assignment.model;

public class PointsTransaction {
	private int custID;
	private String date;
	private double points;
	private String transaction;
	
	public PointsTransaction() {
		
	}
	
	public int getCustID() {
		return custID;
	}
	public void setCustID(int custID) {
		this.custID = custID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	public String getTransaction() {
		return transaction;
	}
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	public PointsTransaction(int custID, String date, double points, String transaction) {
		super();
		this.custID = custID;
		this.date = date;
		this.points = points;
		this.transaction = transaction;
	}	
	
}
