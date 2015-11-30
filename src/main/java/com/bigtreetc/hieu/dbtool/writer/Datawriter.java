package com.bigtreetc.hieu.dbtool.writer;

import java.io.IOException;

public interface Datawriter {
	int writeHeader(String[] headers);
	
	int writeRowValues(Object[] values);
	
	void close() throws IOException;
}
