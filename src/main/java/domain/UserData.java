package domain;

public class UserData {
	public String email;
	public String password;
	public String name;
	public String surname;
	
	public UserData(String email, String pass, String name, String surname) {
		this.email = email;
		this.password = pass;
		this.name = name;
		this.surname = surname;
	}
}
