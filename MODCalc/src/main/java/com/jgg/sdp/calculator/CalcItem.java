package com.jgg.sdp.calculator;

import java.math.BigDecimal;

import java_cup.runtime.Symbol;

public class CalcItem {

	private Symbol sym = null;
	
	private boolean array = false;
	public BigDecimal[] values = new BigDecimal[1];
	
	public CalcItem(String value)  { values[0] = new BigDecimal(value); }
	public CalcItem(Integer value) { values[0] = new BigDecimal(value); }
	public CalcItem(Long value)    { values[0] = new BigDecimal(value); }
	public CalcItem(double value)  { values[0] = new BigDecimal(value); }
	
	public CalcItem(int[] values)   { 
		loadArray(values);
	}
	public CalcItem(long[] values)  { 
		loadArray(values);
	}

	// Constructor de copia
	public CalcItem(CalcItem c) {
		this.sym = c.getSymbol();
		this.values = new BigDecimal[c.values.length];
		for (int idx = 0; idx < c.values.length; idx++) {
			values[idx] = c.values[idx].add(new BigDecimal(0));
		}
		this.array = this.values.length > 1;
	}
	
	public Symbol  getSymbol() { return sym; }
	public void    setSymbol(Symbol s) { sym = s; }
	public boolean isArray() { return array;            }
	public int     getSize() { return values.length;    }
	public String  getName() { return (String) sym.value; }

	/******************************************************************/
	/* OPERACIONES DE CALCITEM                                        */
	/******************************************************************/
	
	public CalcItem sum() {
		BigDecimal tmp = new BigDecimal(0);
		CalcItem c = new CalcItem(this);
		for (int idx = 0; idx < values.length; idx++) {
			tmp = tmp.add(values[idx]);
		}
		c.removeArray(tmp);
		return c;
	}

	public CalcItem prod() {
		BigDecimal tmp = new BigDecimal(0);
		CalcItem c = new CalcItem(this);
		for (int idx = 0; idx < values.length; idx++) {
			tmp = tmp.multiply(values[idx]);
		}
		c.removeArray(tmp);
		return c;
	}

	public int compare(CalcItem item) {
		int res = 0;
		CalcItem c = adjustArrays(item);
		for (int idx = 0; idx < c.values.length; idx++) {
			res = values[idx].compareTo(item.values[idx]);
			if (res != 0) return res;
		}
		return 0;
	}
	
	/******************************************************************/
	/* OPERACIONES DE BIG DECIMAL                                     */
	/******************************************************************/

	public int intValue() {
		return values[0].intValue();
	}
	
	public double doubleValue() {
		return values[0].doubleValue();
	}
	
	// Abs no deberia cambiar los valores originales
	// Asi que crea otro objeto
	public CalcItem abs() {
		CalcItem c = new CalcItem(this);
		for (int idx = 0; idx < values.length; idx++) c.values[idx] = values[idx].abs();
		return c;
	}
	
	public CalcItem add(CalcItem item) {
		CalcItem c = adjustArrays(item);
		for (int idx = 0; idx < values.length; idx++) {
			c.values[idx] = values[idx].add(item.getValue(idx));
		}	
		return c;
	}

	public CalcItem subtract(CalcItem item) {
		CalcItem c = adjustArrays(item);
		for (int idx = 0; idx < values.length; idx++) {
			c.values[idx] = values[idx].subtract(item.getValue(idx));
		}	
		return c;
	}
	
	public CalcItem divide(CalcItem item) {
		CalcItem c = adjustArrays(item);
		for (int idx = 0; idx < values.length; idx++) {
			c.values[idx] = values[idx].divide(item.getValue(idx));
		}	
		return c;
	}
	
	public CalcItem multiply(CalcItem item) {
		CalcItem c = adjustArrays(item);
		for (int idx = 0; idx < values.length; idx++) {
			c.values[idx] = values[idx].multiply(item.getValue(idx));
		}	
		return c;
	}

	public CalcItem remainder(CalcItem item) {
		CalcItem c = adjustArrays(item);
		for (int idx = 0; idx < values.length; idx++) {
			c.values[idx] = values[idx].add(item.getValue(idx));
		}	
		return c;
	}

	public CalcItem pow(int n) {
		CalcItem c = adjustArrays(this);
		for (int idx = 0; idx < values.length; idx++) {
			c.values[idx] = values[idx].pow(n);
		}	
		return c;
	}

	private void loadArray(long[] datos) {
		array = true;
		values = new BigDecimal[datos.length];
		for (int idx = 0; idx < datos.length; idx++) values[idx] = new BigDecimal(datos[idx]);
	}
	private void loadArray(int[] datos) {
		array = true;
		values = new BigDecimal[datos.length];
		for (int idx = 0; idx < datos.length; idx++) values[idx] = new BigDecimal(datos[idx]);
	}
	

	private void removeArray(BigDecimal dato) {
		values = new BigDecimal[1];
		values[0] = dato;
		array = false;
	}
	
	
	private CalcItem adjustArrays(CalcItem item) {
		CalcItem c = new CalcItem(this);
		checkArray(c, item);
		
		if (!item.isArray()) item.makeArray(c);
		if (!c.isArray())    c.makeArray(item);
        return c;
	}

	private void checkArray(CalcItem t, CalcItem other) {
		if (t.isArray() && other.isArray()) {
			if (t.getSize() != other.getSize()) {
				throw new CalculatorException(sym, "Dimensions of " + t.getName() + " and " + other.getName() + "are not equals");
			}
		}
	}
	
	private void makeArray(CalcItem item) {
		array = true;
		BigDecimal aux = values[0];
		values = new BigDecimal[item.getSize()];
		for (int idx = 0; idx < values.length; idx++) values[idx] = aux;
	}	
	public BigDecimal getValue() {
		return values[0];
	}
	public BigDecimal getValue(int idx) {
		return values[idx];
	}
	
	public void print(String txt) {
		System.out.println(txt);
		print();
	}
	
	public void print() {
		for (int idx = 0; idx < values.length; idx++) {
			System.out.println(idx + ": " + values[idx]);
		}
		System.out.println("-------------------------------------------------");
	}
}
