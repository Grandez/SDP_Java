package com.jgg.sdp.calc;

public class MainCalc {

	private Object base = null;
	private Object root = null;
	
	public static void main(String[] args) throws Exception {
        MainCalc calc = new MainCalc();
        calc.process();

	}

	private void process() throws Exception {
		String[] data = { "1 + 2"
				         ," 3 + 4 * 5"
				         ,"0 * 0"
				         , "0 / 0"
				         ,"(3 + 4) * 5"
				         ,"1 / 3"
				         };
		
		for (int idx = 0; idx < data.length; idx++) {
			System.out.print(data[idx] + " = ");
			try {
			   Calculator c = new Calculator(data[idx]);
			   c.setObjectBase(base);
			   c.setObjectRoot(root);
			   System.out.println(c.evaluateExpression());
			}
			catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}
