package sfg.assignment.model;

public class UserAccount {
	protected int id;
	protected String name;
	protected String surname;
	protected String email;
	private String password;
	
	public UserAccount(int id,String name, String surname, String email, String password) {
		super();
		this.id=id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
	}

	public UserAccount() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password="
				+ password + "]";
	}
	
	

}
