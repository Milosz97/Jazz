package model;

public class User {

	String userName;
	String password;
	String email;
	TypeOfAccount typeOfAccount;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TypeOfAccount getTypeOfAccount() {
		return typeOfAccount;
	}

	public void setTypeOfAccount(TypeOfAccount typeOfAccount) {
		this.typeOfAccount = typeOfAccount;
	}

	public User(String userName, String password, String email, TypeOfAccount typeOfAccount) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.typeOfAccount = typeOfAccount;
	}
}
