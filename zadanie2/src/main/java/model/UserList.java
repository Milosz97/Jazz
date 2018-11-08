package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserList {

	private static List<User> listOfUsers = new ArrayList<User>(Arrays
			.asList(new User("admin", "admin1234", "admin@admin.admin", TypeOfAccount.ADMIN)));

	public static void addNewUser(User user) {
		listOfUsers.add(user);
	}

	public static List<User> getListOfUsers() {
		return listOfUsers;
	}
}
