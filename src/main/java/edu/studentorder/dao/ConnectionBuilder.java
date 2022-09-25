package edu.studentorder.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import edu.studentorder.config.Config;

public class ConnectionBuilder {
	
	public static Connection getConnection() throws SQLException {
		Connection con = DriverManager.getConnection(
				Config.getProperty(Config.DB_URL),
				Config.getProperty(Config.DB_LOGIN), 
				Config.getProperty(Config.DB_PASSWORD));
		return con;	
	}
}