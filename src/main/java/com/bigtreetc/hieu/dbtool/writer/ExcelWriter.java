package com.bigtreetc.hieu.dbtool.writer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelWriter implements Datawriter {
	private String outputFilePath = null;
	private HSSFWorkbook workbook = null;
	private int currentRow = 0;

	public ExcelWriter(String outputFilePath) {
		this.outputFilePath = outputFilePath;
		workbook = new HSSFWorkbook();
	}

	public boolean writeSheet(String sheetname, String[] headers, List<Object[]> rowValues) {
		Sheet sheet = workbook.createSheet(sheetname);
		writeRow(sheet, headers);
		for (Object[] rowValue : rowValues) {
			writeRow(sheet, rowValue);
		}
		return true;
	}

	public void writeToFile() throws IOException {
		OutputStream outputStream = new FileOutputStream(outputFilePath);
		workbook.write(outputStream);
		outputStream.close();
	}

	private void writeRow(Sheet sheet, Object[] values) {
		Row row = sheet.getRow(currentRow);
		if (row == null) {
			row = sheet.createRow(currentRow);
		}
		currentRow++;

		Cell cell = null;

		for (int cellIndex = 0; cellIndex < values.length; cellIndex++) {
			cell = row.getCell(cellIndex);
			if (cell == null) {
				cell = row.createCell(cellIndex);
			}

			Object value = values[cellIndex];

			if (value == null)
				continue;

			if (value instanceof Date) {
				cell.setCellValue((Date) value);
			} else if (value instanceof Number) {
				cell.setCellValue(((Number) value).doubleValue());
			} else if (value instanceof Boolean) {
				cell.setCellValue((Boolean) value);
			} else {
				cell.setCellValue(value.toString());
			}
		}
	}
}
