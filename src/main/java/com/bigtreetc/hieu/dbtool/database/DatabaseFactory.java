package com.bigtreetc.hieu.dbtool.database;

import java.sql.Connection;

public class DatabaseFactory {
	public static final int ORACLE = 1;

	private static int currentDatabase = 0;

	private static Connection connection;
	private static String URL;
	private static String user;
	private static String password;

	public static Connection getConnection() {
		return connection;
	}

	public void init(int database) {
		switch (database) {
		case ORACLE:

			break;

		default:
			break;
		}
	}

	public static void changeDatabase(int database, String URL, String user, String password) {
		currentDatabase = database;
		init(URL, user, password);
	}

	public static void init(String URL, String user, String password) {
		DatabaseFactory.URL = URL;
		DatabaseFactory.user = user;
		DatabaseFactory.password = password;
	}
}
