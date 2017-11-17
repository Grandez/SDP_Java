package com.jgg.sdp.module.work;

import java.util.ArrayList;

public class CommentBlock {

	private ArrayList<CommentLine> comments = new ArrayList<CommentLine>();
	private ArrayList<Integer>     lines    = new ArrayList<Integer>();
	
	private StringBuilder sb = null;
	
	public void addLine(String txt, int line) {
		sb = new StringBuilder(txt);
		lines.add(line);
	}
	public void appendLine(String txt) {
		sb.append(txt);
	}
	public void endLine(Integer line) {
		comments.add(new CommentLine(sb.toString(), line));
	}

	public CommentLine getLastComment() {
		if (comments.size() == 0) return null;
		return comments.get(comments.size() - 1);
	}
}
