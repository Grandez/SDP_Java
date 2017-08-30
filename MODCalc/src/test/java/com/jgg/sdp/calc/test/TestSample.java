package com.jgg.sdp.calc.test;

import com.jgg.sdp.calc.Calculator;

public class TestSample {

	private Object base = new SampleIssues();
	private Object root = null;
	
	public static void main(String[] args) throws Exception {
        TestSample calc = new TestSample();
        calc.process();

	}

	private void process() throws Exception {
			try {
			   Calculator c = new Calculator("SUM($ISSUES_NUM * ($ISSUES_LEVEL + 1)) / SUM($ISSUES_NUM)");
			   c.setObjectBase(base);
			   c.setObjectRoot(root);
			   System.out.println(c.evaluateExpression());
			}
			catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
	}
}
