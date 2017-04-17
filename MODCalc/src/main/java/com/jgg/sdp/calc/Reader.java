package com.jgg.sdp.calc;

import java.io.IOException;

public class Reader extends java.io.Reader {

	String buffer = null;
	int    size   = 0;
	int    offset = -1;

	public Reader(String data) {
		buffer = data;
		offset = 0;
		size = buffer.length();
	}

	@Override	
	public int read() {
		if (offset == size) return -1;
		return buffer.charAt(offset++);
	}
	
	public int read(char[] cbuf) throws IOException {
		return read(cbuf, 0, cbuf.length);
	}
	
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		if (offset == size) return -1;
		for (int cnt = 0; cnt < len; cnt++) {
			if (offset == size) return cnt;
			cbuf[off + cnt] = buffer.charAt(offset++);
		}
		return len;
	}

	@Override
	public void close() throws IOException {
       offset = 0;
	}
	
	
}
