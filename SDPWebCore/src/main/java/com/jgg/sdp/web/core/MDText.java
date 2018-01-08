package com.jgg.sdp.web.core;

import java.util.List;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import com.jgg.sdp.tools.Cadena;

public class MDText {

	public String renderBlock(List<String> data) {
		StringBuilder sb = new StringBuilder();
		for (String s : data) {
			sb.append(Cadena.rtrim(s));
			sb.append('\n');
		}
		return render(sb.toString());
	}
	
	public String renderText(String data) {
		return render(data);
	}
	
	private String render(String txt) {
		if (txt == null) return null;
		Parser parser = Parser.builder().build();
		Node document = parser.parse(txt);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(document);		
	}
}
