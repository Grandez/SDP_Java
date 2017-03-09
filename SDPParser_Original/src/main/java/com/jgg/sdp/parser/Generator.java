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
import com.jgg.sdp.parser.base.InjectorSingleton;
import com.jgg.sdp.parser.tools.Injector;
import com.jgg.sdp.module.base.*;
import com.jgg.sdp.module.items.*;
import com.jgg.sdp.module.unit.Source;

public class Generator {

	private Module   module = null;
	private Source   source = null;
	private Injector injector = null;
	private String[] lineas = null;

	private int leftMargin = 0;
	
    private StringBuilder  buffer = null;    
	
	private Configuration cfg = Configuration.getInstance();
	
	public Generator(Source source, Module module) {
		this.module = module;
		this.source = source;
		lineas      = (new String(this.source.getRawData())).split("\\r?\\n");
		injector    = InjectorSingleton.getInjector();
		leftMargin  = cfg.getInteger(CFG.MARGIN_LEFT, 0);
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
		
		buffer = new StringBuilder();

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
//JGG			generateOutput("           .");
		}
			
		flushBuffer();        
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

	private void generateOutput(ArrayList<String> lineas) {
		for (String linea: lineas) generateOutput(linea);
	}
	
	private void generateOutput(String linea) {
		buffer.append(linea);
		buffer.append(System.lineSeparator());
	}

	private void flushBuffer() {
		
		try {
            BufferedWriter out = openOutputFile();
	        out.write(buffer.toString());
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// En el nuevo modulo fuente se crea una variable
		// Snnnnnnn que varia en cada ejecucion
		// Para el Hash lo susituimos por una S
		String d = buffer.toString();
		d = d.replaceAll(module.getSDPGroup(), "S");
		module.setNewHash(Firma.calculate(d.getBytes()));
		
	}
	
	private BufferedWriter openOutputFile() throws IOException {
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
		if (!file.exists()) file.createNewFile();
 
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		return new BufferedWriter(fw);
	}
	
}
