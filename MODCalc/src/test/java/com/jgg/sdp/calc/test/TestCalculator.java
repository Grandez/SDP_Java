package com.jgg.sdp.calc.test;

import java.math.BigDecimal;

import org.testng.*;
import org.testng.annotations.*;

import com.jgg.sdp.calc.Calculator;

public class TestCalculator {

   @Test(dataProvider = "values")
   public void testValues(String formula, BigDecimal value) {
	   Calculator calc = new Calculator(formula);
	   
	   try {
			Assert.assertEquals(calc.evaluateExpression(), value);
	   } catch (Exception e) {
		   Assert.fail("Excepcion generada");
	   }
	}

   @Test(dataProvider = "relational")
   public void testBoolean(String formula, boolean value) {
	   Calculator calc = new Calculator(formula);
	   
	   try {
			Assert.assertEquals(calc.evaluateLogicalExpression(), value);
	   } catch (Exception e) {
		   Assert.fail("Excepcion generada");
	   }
	}

	@DataProvider(name = "values")
	public Object[][] provideDataValues() {

		return new Object[][] { 
			{ "1 + 2"                  , new BigDecimal( 3) } 
		   ,{ " 2 + 3 * 4"             , new BigDecimal(14) }
		   ,{ " (2 + 3) * 4"           , new BigDecimal(20) }
		   ,{ "  2 + (3 * 4)"          , new BigDecimal(14) } 		   
		};
	}
   
	@DataProvider(name = "relational")
	public Object[][] provideDataRelational() {

		return new Object[][] { 
			{ "1 + 2 = 3"              , true  } 
		   ,{ "1 > 2"                  , false }
		   ,{ "2 <= 2"                 , true  } 
		};
	}
	 

}
