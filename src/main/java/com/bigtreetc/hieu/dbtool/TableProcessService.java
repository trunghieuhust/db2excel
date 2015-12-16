package com.bigtreetc.hieu.dbtool;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

import com.bigtreetc.hieu.dbtool.database.OracleDatabase;
import com.bigtreetc.hieu.dbtool.writer.Datawriter;
import com.bigtreetc.hieu.dbtool.writer.ExcelWriter;

public class TableProcessService {
	private static List<String> tables = new ArrayList<String>();

	public static void refresh() {

	}

	public static List<String> parse(String sqlQuery) {
		List<String> existTables = new ArrayList<String>();

		if (sqlQuery != null && sqlQuery.length() > 0) {
			StringTokenizer tokenizer = new StringTokenizer(sqlQuery);
			while (tokenizer.hasMoreTokens()) {
				String element = tokenizer.nextToken();
				if (isTableName(element)) {
					insertToTableList(element, existTables);
				}
			}
		}
		return existTables;
	}

	private static boolean isTableName(String element) {
		if (tables.contains(element)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean insertToTableList(String element, List<String> tables) {
		if (tables.contains(element)) {
			return false;
		} else {
			tables.add(element);
			return true;
		}
	}

	public static void main(String[] args) {
		try {
			String selectSQL = "select * from ";
			Connection connection;
			OracleDatabase.getInstance().init("192.168.31.98", "mop2user", "mop2user");
			connection = OracleDatabase.getInstance().getConnection();

			ResultSet resultSet = connection.createStatement()
					.executeQuery("SELECT table_name FROM all_tables where owner='MOP2USER' ");
			while (resultSet.next()) {
				tables.add(resultSet.getString(1).toLowerCase());
			}
			String sqlquery = FileUtils.readFileToString(
					new File(
							"C:/Users/hieu_bui/git/mop/ats-persistence/src/main/resources/sql/job/select_jobByKey.sql"),
					Charset.forName("UTF-8"));
			List<String> result = parse(sqlquery);

			Datawriter writer = new ExcelWriter("D:\\test.xls");
			for (String string : result) {
				ResultSet table = connection.createStatement().executeQuery(selectSQL + string);
				ResultSetMetaData metaData = table.getMetaData();

				System.out.println(string);
				String[] headers = new String[metaData.getColumnCount()];
				for (int i = 0; i < headers.length; i++) {
					headers[i] = metaData.getColumnLabel(i + 1);
					System.out.print(headers[i] + "  |  ");
				}
				System.out.println();
				List<Object[]> rowValues = new ArrayList<Object[]>();
				while (table.next()) {
					Object[] rowValue = new Object[headers.length];
					for (int i = 0; i < rowValue.length; i++) {
						rowValue[i] = table.getObject(i + 1);
					}
					rowValues.add(rowValue);
				}

				writer.writeSheet(string, headers, rowValues);
				table.close();
			}
			writer.writeToFile();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
