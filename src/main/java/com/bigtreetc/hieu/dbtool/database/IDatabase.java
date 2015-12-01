package com.bigtreetc.hieu.dbtool.database;

import java.sql.Connection;

public interface IDatabase {
	public void init(String url, String username, String password);

	public Connection getConnection();

	public void closeConnection();
}
