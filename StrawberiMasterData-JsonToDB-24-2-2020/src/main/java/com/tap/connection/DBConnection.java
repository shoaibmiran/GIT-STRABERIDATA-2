package com.tap.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	public static Connection getConnection() throws Exception {
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "fitjsontodb";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "tiger";

		Class.forName(driver).newInstance();
		Connection conn = DriverManager.getConnection(url + dbName, userName, password);

		return conn;
	}

	public static void closeConnection(Connection conn) {

		try {

			if (conn != null)
				conn.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public static void closeStatement(Statement stmt) {

		try {

			if (stmt != null)
				stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void closeResultSet(ResultSet rs) {

		try {

			if (rs != null)
				rs.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

}
