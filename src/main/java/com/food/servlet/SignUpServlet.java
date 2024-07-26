package com.food.servlet;

import java.io.IOException;

import com.food.DAOs.UserDAO;
import com.food.DaoImp.UserImp;
import com.food.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/sighup")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserDAO userdao;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		userdao = new UserImp();
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String confirmPassword = req.getParameter("confirmPassword");
		String password = req.getParameter("password");
		String address = req.getParameter("address");
		String role = req.getParameter("role");

		if (password.equals(confirmPassword)) {

			int x = userdao.addUser(new User(username, password, email, address, role));

			if (x != 0) {
				resp.sendRedirect("login.jsp");
			} else {
				resp.sendRedirect("signupUnsucessful.jsp");
			}
		} else {
			resp.sendRedirect("passwordError.jsp");
		}

	}
}