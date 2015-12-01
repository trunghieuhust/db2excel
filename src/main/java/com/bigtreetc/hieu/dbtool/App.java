package com.bigtreetc.hieu.dbtool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bigtreetc.hieu.dbtool.database.DatabaseFactory;
import com.bigtreetc.hieu.dbtool.database.IDatabase;
import com.bigtreetc.hieu.dbtool.writer.Datawriter;
import com.bigtreetc.hieu.dbtool.writer.ExcelWriter;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		IDatabase database = DatabaseFactory.getDatabase(DatabaseFactory.ORACLE);
		database.init("192.168.31.98", "mop2user", "mop2user");
		Connection connection = database.getConnection();
		ResultSet resultSet;
		try {
			resultSet = connection.createStatement()
					.executeQuery("SELECT owner, table_name FROM all_tables where owner='MOP2USER' ");
			ResultSetMetaData metaData = resultSet.getMetaData();
			String[] headers = new String[metaData.getColumnCount()];
			for (int i = 0; i < headers.length; i++) {
				headers[i] = metaData.getColumnLabel(i + 1);
			}

			List<Object[]> rowValues = new ArrayList<Object[]>();
			while (resultSet.next()) {
				Object[] rowValue = new Object[headers.length];
				for (int i = 0; i < rowValue.length; i++) {
					rowValue[i] = resultSet.getObject(i + 1);
				}
				rowValues.add(rowValue);
			}
			Datawriter writer = new ExcelWriter("D:\\test.xls");
			writer.writeSheet("TEST", headers, rowValues);
			try {
				writer.writeToFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
