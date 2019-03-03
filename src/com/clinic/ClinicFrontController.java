package com.clinic;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */

public class ClinicFrontController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		PrintWriter out = res.getWriter();
		String driver = "com.mysql.jdbc.Driver";
		String dburl = "jdbc:mysql://localhost:3306/test";
		String dbuser = "mysql";
		String dbpass = "mysql";

		try {
			Class.forName(driver);
			// com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
			// DriverManager.registerDriver(driver);
			Connection conn = DriverManager
					.getConnection(dburl, dbuser, dbpass);
			Statement stmt = conn.createStatement();
			String sql;
			String id = req.getParameter("id");
			String service = req.getParameter("service");
			sql = "SELECT username FROM registration WHERE id=" + id;
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				String name = rs.getString("username");
				out.println("<h1>" + "Registered user : " + name + "</h1>");
				out.println("<h1>" + "Service requested : " + service + "</h1>");
				DepartmentDispatcher dispatcher = new DepartmentDispatcher();
				boolean isServiceAvailable = dispatcher.DispatcherRequest(service);
				if (isServiceAvailable){
					out.println("<h1>" + "Welcome to " + service + " Department" + "</h1>");
				} else{
					out.println("<h1>" + "Sorry, requested service is not available" + "</h1>");
				}
			} else {
				out.println("<h1>" + "Id " + id + " is not registered" + "</h1>");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}