/**
 * Se encarga de generar el nuevo fichero con los TRAPS incluidos
 *   
 * @author Javier Gonzalez Grandez
 * @version 3.0
 *   
 */
package com.jgg.sdp.parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.jgg.sdp.core.config.Configuration;
import com.jgg.sdp.core.ctes.CFG;
import com.jgg.sdp.core.tools.*;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.module.tools.Injector;

public class Generator {

	Source   source = null;
	Injector injector = null;
	String[] lineas = null;

	int leftMargin = 0;
	
	BufferedWriter out = null;

	private Configuration cfg = Configuration.getInstance();
	
	/**
     * Instancia un nuevo Generator
     *
     * @param source
     *            El código fuente original
     * @param module
     *            El objeto Module con la informacion de inyeccion
     */
	public Generator(Source source, Module module) {
		this.source = source;
		lineas = (new String(this.source.getRawData())).split("\\r?\\n");
		injector = module.getInjector();
		leftMargin = cfg.getInteger(CFG.MARGIN_LEFT, 0);
	}
	
	/**
	 * Genera el nuevo fichero
	 * La injeccion se debe hacer por linea antes y despues
	 * ejemplo: 
	 *      14 MOVE xxxxx TO
	 *      15      yyyyyy
	 *      Hay que inyectar antes de 14 Y despues de 15
	 */
	public void generate() {
		int idx;
		int nextLine;
		Formatter formatter = new Formatter();

		openOutputFile();

		nextLine = injector.getNextInjection();
		
		for (idx = 0; idx < lineas.length; idx++) {

			while (idx == nextLine) {
				lineas[idx] = checkIfSplitLine(injector.getColumn(), lineas[idx]);
				for (String line : injector.getCodeToInject()) {
					generateOutput(line);					
				}
				nextLine = injector.getNextInjection();
			}
			generateOutput(lineas[idx]);
		}
		
		// Hay que insertar codigo al final del fichero
		if (idx < nextLine ) {
			int column = injector.getColumn();
			for (String line : injector.getCodeToInject()) {
				generateOutput(formatter.format(line, column));					
			}
			// Forzar el punto final
			generateOutput("           .");
		}
			
        closeOutputFile();
        
	}

	/**
	 * Verifica si el codigo se debe inyectar en mitad de la linea
	 * Si es asi, la linea se parte y en esta funcion se pone la primera parte
	 * Ejemplo:
	 *     WHEN A CALL xxxx
	 *     Quedaria como
	 *     WHEN A
	 *     IF ...
	 *     CALL SDPTRAP
	 *     END IF
	 *     CALL xxxx
	 * @param column
	 * @param line
	 * @return la linea original o la parte restante que hay que imprimir
	 */
	private String checkIfSplitLine(int column, String line) {
		int col = column + leftMargin;
		if (column == 0) return line;

		String filler = line.substring(leftMargin, col - 1).trim();
		if (filler.length() == 0) return line;
		generateOutput(line.substring(0,  col - 1));
		StringBuilder builder = new StringBuilder();
		for (int idx = 0; idx < col - 1; idx++) builder.append(' ');
		builder.append(line.substring(col - 1));
		return builder.toString();

	}

	private void openOutputFile() {
		Archivo file = new Archivo(source.getFullName());
		String newFile = cfg.getTempName(); 
		
		if (newFile == null) {
		    String base = cfg.getOutputDir();
		    if (base != null) {
		        newFile = base + file.getFileName();
		    }
		    else {
		        newFile = file.getFileName();
		    }
		}
		
		file = new Archivo(newFile);
		try {
			if (!file.exists()) file.createNewFile();
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			out = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void closeOutputFile() {
		try {
		   out.close();
		}
		catch (IOException e) {
			// Do nothing
		}
	}
	
	private void generateOutput(ArrayList<String> lineas) {
		for (String linea: lineas) generateOutput(linea);
	}
	
	private void generateOutput(String linea) {
			try {
				out.write(linea);
				out.write("\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}
