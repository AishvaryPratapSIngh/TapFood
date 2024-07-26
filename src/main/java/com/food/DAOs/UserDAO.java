package com.food.DAOs;

import com.food.model.User;
import java.util.List;

public interface UserDAO {
	int addUser(User user);

	User getUser(String email);

	void deleteUser(int user);

	void updateUser(User user);

	List<User> getAll();

}
