package com.bigtreetc.hieu.dbtool.writer;

import java.io.IOException;
import java.util.List;

public interface Datawriter {
	public boolean writeSheet(String sheetname, String[] headers, List<Object[]> rowValues);

	void writeToFile() throws IOException;
}
