package com.bigtreetc.hieu.dbtool.database;

public class DatabaseFactory {
	public static final int ORACLE = 0;

	public static IDatabase getDatabase(int database) {
		switch (database) {
		case 0:
			return new OracleDatabase();

		default:
			return null;
		}
	}
}
