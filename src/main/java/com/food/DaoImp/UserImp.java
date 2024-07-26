package com.food.DaoImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.food.DAOs.UserDAO;
import com.food.model.User;

public class UserImp implements UserDAO {
	private Connection con = null;
	private String url = "jdbc:mysql://localhost:3306/tapfood";
	private String username = "root";
	private String password = "aish0305";
	String insertQuery = "INSERT INTO `user` (`user_name`,`password`,`email`,`address`,`role`) VALUES (?,?,?,?,?)";
	String retreiveQuery = "SELECT * FROM `user` WHERE `email` = ?";
	String updateQuery = "UPDATE `user` SET `user_name` = ? , `password` = ? , `email` = ? , `address` = ? , `role` = ? WHERE `user_id` = ?";
	String deleteQuery = "DELETE FROM `user` WHERE `user_id` = ?";
	String selectQuery = "Select * FROM `user`";
	PreparedStatement ps = null;
	Statement st = null;
	ResultSet res = null;

	public UserImp() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int addUser(User user) {
		try {
			ps = con.prepareStatement(insertQuery);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getAddress());
			ps.setString(5, user.getRole());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public User getUser(String email) {
		User user = null;
		try {
			ps = con.prepareStatement(retreiveQuery);
			ps.setString(1, email);
			ResultSet res = ps.executeQuery();
			if (res.next()) {
				user = extractUserFromResultSet(res);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void deleteUser(int user) {
		try {
			ps = con.prepareStatement(deleteQuery);
			ps.setInt(1, user);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateUser(User user) {
		try {
			ps = con.prepareStatement(updateQuery);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getAddress());
			ps.setString(5, user.getRole());
			ps.setInt(6, user.getId());
			System.out.println("row affected: " + ps.executeUpdate());

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private User extractUserFromResultSet(ResultSet res) throws SQLException {
		User user = new User();
		user.setId(res.getInt("user_id"));
		user.setUsername(res.getString("user_name"));
		user.setPassword(res.getString("password"));
		user.setEmail(res.getString("email"));
		user.setAddress(res.getString("address"));
		user.setRole(res.getString("role"));
		return user;
	}

	@Override
	public List<User> getAll() {
		List<User> al = new ArrayList<>();
		try {
			st = con.createStatement();
			res = st.executeQuery(selectQuery);
			while (res.next()) {
				User u = extractUserFromResultSet(res);
				al.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
