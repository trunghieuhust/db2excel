package com.bigtreetc.hieu.dbtool.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDatabase implements IDatabase {
	private static String URL = "127.0.0.1";
	private static String username = "";
	private static String password = "";
	private static OracleDatabase instance;

	private static Connection connection = null;

	public void init(String url, String username, String password) {
		if (url != null && username != null && password != null) {
			OracleDatabase.URL = url;
			OracleDatabase.username = username;
			OracleDatabase.password = password;

			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			try {
				connection = DriverManager.getConnection("jdbc:oracle:thin:@" + URL + ":1521:XE",
						OracleDatabase.username, OracleDatabase.password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static OracleDatabase getInstance() {
		synchronized (OracleDatabase.class) {
			if (instance != null) {
				return instance;
			} else {
				return new OracleDatabase();
			}
		}
	}

	public static String getURL() {
		return URL;
	}

	public static void setURL(String uRL) {
		URL = uRL;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		OracleDatabase.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		OracleDatabase.password = password;
	}
}
